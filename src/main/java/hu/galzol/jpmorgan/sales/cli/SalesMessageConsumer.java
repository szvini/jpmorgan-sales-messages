package hu.galzol.jpmorgan.sales.cli;

import hu.galzol.jpmorgan.sales.message.SalesMessageConsoleReporter;
import hu.galzol.jpmorgan.sales.message.SalesMessageReceiver;
import hu.galzol.jpmorgan.sales.message.SalesMessageReporter;
import hu.galzol.jpmorgan.sales.product.Operation;
import hu.galzol.jpmorgan.sales.storage.SalesMessageMemoryStorage;
import hu.galzol.jpmorgan.sales.storage.SalesMessageStorage;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class SalesMessageConsumer {

    private final SalesMessageStorage storage = new SalesMessageMemoryStorage();
    private final SalesMessageReporter reporter = new SalesMessageConsoleReporter(storage);
    private final SalesMessageReceiver receiver = new SalesMessageReceiver(storage, reporter, 2, 10);

    private Scanner scanner = new Scanner(System.in).useLocale(Locale.ENGLISH);

    public static void main(String... args) {
        new SalesMessageConsumer().start();
    }

    private void start() {
        try {
            while (!receiver.hasReachedMaximumNumberOfMessages()) {
                int type = readMessageType();
                switch (type) {
                    case 1:
                        pushProductSale();
                        break;
                    case 2:
                        pushProductSaleWithOccurrence();
                        break;
                    case 3:
                        pushProductSaleAdjustment();
                        break;
                }
            }
            System.out.println("\nApplication is pausing, no more message accepted.");
        } finally {
            scanner.close();
        }
    }

    private int readMessageType() {
        try {
            System.out.println("Choose a message type number (1: product sale, 2: product sale with quantity, 3: product sale adjustment): ");
            int type = Integer.parseInt(scanner.next().trim());
            if (type < 1 || type > 3) throw new InputMismatchException();
            return type;
        } catch (NumberFormatException | InputMismatchException e) {
            return readMessageType();
        }
    }

    private void pushProductSale() {
        try {
            System.out.println("Enter sales details (type: String, value: Number). E.g: apple 10");
            String type = scanner.next().trim();
            if (type.isEmpty()) throw new InputMismatchException();
            BigDecimal value = scanner.nextBigDecimal();

            receiver.receiveProduct(type, value);
        } catch (NumberFormatException | InputMismatchException e) {
            pushProductSale();
        }
    }

    private void pushProductSaleWithOccurrence() {
        try {
            System.out.println("Enter sales details (type: String, value: Number, occurrence: Number > 0). E.g: apple 10 3");
            String type = scanner.next().trim();
            if (type.isEmpty()) throw new InputMismatchException();
            BigDecimal value = scanner.nextBigDecimal();
            Integer qty = scanner.nextInt();
            if (qty < 1) throw new InputMismatchException();

            receiver.receiveProduct(type, value, qty);
        } catch (NumberFormatException | InputMismatchException e) {
            pushProductSale();
        }
    }

    private void pushProductSaleAdjustment() {
        try {
            System.out.println("Enter sales adjustment details (type: String, value: Number, operation: ADD|SUBTRACT|MULTIPLY). E.g: apple 10 ADD");
            String type = scanner.next().trim();
            if (type.isEmpty()) throw new InputMismatchException();
            BigDecimal value = scanner.nextBigDecimal();
            Operation op = Operation.valueOf(scanner.next().trim());

            receiver.receiveAdjustment(type, value, op);
        } catch (InputMismatchException | IllegalArgumentException e) {
            pushProductSale();
        }
    }

}
