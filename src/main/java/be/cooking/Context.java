package be.cooking;

import be.cooking.model.*;

import java.util.Arrays;
import java.util.List;

class Context {
    final Topic topic = new Topic();
    final ThreadedHandler orderPrinter = createActor("Printer", new OrderPrinter());
    final Cashier cashier = new Cashier(topic);
    final ThreadedHandler threadCashier = createActor("MoneyMan", cashier);
    final ThreadedHandler manager = createActor("Manager", new Manager(topic));

    final Cook cookKoen = new Cook(topic, "Koen", 350);
    final TTLChecker ttlCookKoen = new TTLChecker(cookKoen);
    final ThreadedHandler koen = createActor("Cook Koen", ttlCookKoen);

    final Cook cookGuido = new Cook(topic, "Guido", 200);
    final TTLChecker ttlCookGuido = new TTLChecker(cookGuido);
    final ThreadedHandler guido = createActor("Cook Guido", ttlCookGuido);

    final Cook cookGreg = new Cook(topic, "Greg", 6000);
    final TTLChecker ttlCookGreg = new TTLChecker(cookGreg);
    final ThreadedHandler greg = createActor("Cook Greg", ttlCookGreg);

    final MoreFair cookers = MoreFair.newBuilder()
            .withHandler(koen)
            .withHandler(guido)
            .withHandler(greg)
            .build();

    final Repository<Order> orderRepository = new Repository<>();
    final ThreadedHandler bobTheDistributer = new ThreadedHandler("HandlerBob", cookers);

    final Waiter waiter = new Waiter(topic, orderRepository);
    final List<ThreadedHandler> threadedHandlers = Arrays.asList(orderPrinter, threadCashier, manager, bobTheDistributer, koen, greg, guido);
    final List<Cook> cooks = Arrays.asList(cookGreg, cookGuido, cookKoen);

    private Context() {
    }

    public static Context create() {
        final Context context = new Context();
        context.wire();
        startThreadHandlers(context);
        return context;
    }

    private static void startThreadHandlers(Context context) {
        context.threadedHandlers.forEach(ThreadedHandler::start);
    }

    private static ThreadedHandler createActor(String name, HandleOrder handler) {
        return new ThreadedHandler(name, handler);
    }

    private void wire() {
        topic.subscribe(Topics.PRICE_CALCULATED, threadCashier);
        topic.subscribe(Topics.PAYMENT_DONE, orderPrinter);
        topic.subscribe(Topics.FOOD_READY, manager);
      //  topic.subscribe(Topics.ORDER_PLACED, bobTheDistributer);
    }

}
