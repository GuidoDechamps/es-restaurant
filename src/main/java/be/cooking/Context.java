package be.cooking;

import be.cooking.model.*;

import java.util.Arrays;
import java.util.List;

class Context {
    final ThreadedHandler orderPrinter = createActor("Printer", new OrderPrinter());
    final Cashier cashier = new Cashier(orderPrinter);
    final ThreadedHandler threadCashier = createActor("MoneyMan", cashier);
    final ThreadedHandler manager = createActor("Manager", new Manager(threadCashier));

    final Cook cookKoen = new Cook(manager, "Koen", 350);
    final TTLChecker ttlCookKoen = new TTLChecker(cookKoen);
    final ThreadedHandler koen = createActor("Cook Koen", ttlCookKoen);

    final Cook cookGuido = new Cook(manager, "Guido", 200);
    final TTLChecker ttlCookGuido = new TTLChecker(cookGuido);
    final ThreadedHandler guido = createActor("Cook Guido", ttlCookGuido);

    final Cook cookGreg = new Cook(manager, "Greg", 6000);
    final TTLChecker ttlCookGreg = new TTLChecker(cookGreg);
    final ThreadedHandler greg = createActor("Cook Greg", ttlCookGreg);

    final MoreFair cookers = MoreFair.newBuilder()
            .withHandler(koen)
            .withHandler(guido)
            .withHandler(greg)
            .build();
    final ThreadedHandler threadedHandler = new ThreadedHandler("HandlerBob", cookers);
    final Waiter waiter = new Waiter(cookers);
    final List<ThreadedHandler> threadedHandlers = Arrays.asList(orderPrinter, threadCashier, manager, threadedHandler, koen, greg, guido);
    final List<Cook> cooks = Arrays.asList(cookGreg, cookGuido, cookKoen);
    final List<TTLChecker> ttlCheckers = Arrays.asList(ttlCookGreg, ttlCookGuido, ttlCookKoen);

    private static ThreadedHandler createActor(String name, HandleOrder handler) {
        return new ThreadedHandler(name, handler);
    }

}
