package be.cooking;

import be.cooking.model.*;

import java.util.Arrays;
import java.util.List;

class Context {
    final ThreadedHandler orderPrinter = createActor("Printer", new OrderPrinter());
    final Cashier cashier = new Cashier(orderPrinter);
    final ThreadedHandler threadCashier = createActor("MoneyMan", cashier);
    final ThreadedHandler manager = createActor("Manager", new Manager(threadCashier));
    final ThreadedHandler koen = createActor("Cook Koen", new Cook(manager, "Koen"));
    final ThreadedHandler guido = createActor("Cook Guido", new Cook(manager, "Guido"));
    final ThreadedHandler greg = createActor("Cook Greg", new Cook(manager, "Greg"));
    final RoundRobin repeater = RoundRobin.newBuilder()
            .withHandler(koen)
            .withHandler(guido)
            .withHandler(greg)
            .build();
    final ThreadedHandler threadedHandler = new ThreadedHandler("HandlerBob", repeater);
    final Waiter waiter = new Waiter(repeater);
    final List<ThreadedHandler> threadedHandlers = Arrays.asList(orderPrinter, threadCashier, manager, threadedHandler, koen, greg, guido);

    private static ThreadedHandler createActor(String name, HandleOrder handler) {
        return new ThreadedHandler(name, handler);
    }

}
