package machine;
import java.util.Scanner;

enum ActionType {
    BUY("buy"), FILL("fill"), TAKE("take"), REMAINING("remaining"), EXIT("exit");

    String actionType;

    ActionType(String action){
        this.actionType = action;
    }

    public String getActionType() {
        return this.actionType;
    }
}

enum CoffeeType {
    ESPRESSO(1, 250, 0,16, 1, 4),
    LATTE(2,350, 75, 20, 1, 7),
    CAPPUCCINO(3,200, 100, 12, 1, 6);

    int id;
    int water;
    int milk;
    int beans;
    int cups;
    int costs;

    CoffeeType(int id, int water, int milk, int beans, int cups, int costs){
        this.id = id;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.costs = costs;
        this.cups = cups;
    }

    public static CoffeeType getCoffeeById (int i){
        switch (i) {
            case 1:
                return CoffeeType.ESPRESSO;
            case 2:
                return CoffeeType.LATTE;
            case 3:
                return CoffeeType.CAPPUCCINO;
        }
        return null;
    }
}

class Machine {
    public CoffeeType order;
    public ActionType action;

    int money;
    int water;
    int milk;
    int beans;
    int cups;
    boolean exitFlag = false;

    public void setExitFlag(boolean b){
        this.exitFlag = b;
    }

    public Machine(int money, int water, int milk, int beans, int cups){
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.money = money;
        this.cups = cups;
    }

    public Machine(){
        this.water = 400;
        this.milk = 540;
        this.beans = 120;
        this.money = 550;
        this.cups = 9;
    }

    public void start() {
        do {
            askAction();
        } while(!this.exitFlag);
    }

    public void askAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        String userAction = scanner.next();
        if (userAction.equals(ActionType.BUY.getActionType())) {
            buyCoffee();
        } else if(userAction.equals(ActionType.FILL.getActionType())) {
            fillMachine();
        } else if(userAction.equals(ActionType.REMAINING.getActionType())) {
            getStatus();
        } else if(userAction.equals(ActionType.TAKE.getActionType())) {
            takeMoney();
        } else if(userAction.equals(ActionType.EXIT.getActionType())) {
            exitMachine();
        }
    }

    public void getStatus() {
        System.out.println("The coffee machine has:\n" +
                this.water + " of water\n" +
                this.milk + " of milk\n" +
                this.beans + " of coffee beans\n" +
                this.cups + " of disposable cups\n" +
                this.money + " of money");
    }

    public void buyCoffee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, " +
                "back - to main menu:");
        String userInput = scanner.next();
        if (userInput.equals("back")) {
            askAction();
        } else {
            int id = userInput.equals("1") ? 1 : userInput.equals("2") ? 2 : 3;
            order = CoffeeType.getCoffeeById(id);
            prepareCoffee();
        }
    }

    public boolean checkIfPossible() {
        return this.water >= order.water
                && this.milk >= order.milk
                && this.beans >= order.milk
                && this.cups >= order.cups;
    }

    public void prepareCoffee(){
        if (checkIfPossible()){
            System.out.println("I have enough resources, making you a coffee!");
            this.water -= order.water;
            this.milk -= order.milk;
            this.cups -= order.cups;
            this.beans -= order.beans;
            this.money += order.costs;
        } else {
            if (this.water < order.water) {
                System.out.println("Sorry, not enough water!");
            } else if (this.milk < order.beans) {
                System.out.println("Sorry, not enough mil!");
            } else if (this.beans < order.beans) {
                System.out.println("Sorry, not enough coffee beans!");
            } else {
                System.out.println("Sorry, not enough cups!");
            }
        }
    }

    public void fillMachine() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water do you want to add:");
        this.water += scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        this.milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        this.beans += scanner.nextInt();
        System.out.println("Write how many cups of coffee do you want to add:");
        this.cups += scanner.nextInt();
    }

    public void takeMoney() {
        System.out.println("I gave you $" + this.money);
        this.money -= this.money;
    }

    public void exitMachine() {
        setExitFlag(true);
    }

}

public class CoffeeMachine {

    public static void main(String[] args) {
        Machine machine = new Machine();
        machine.start();
    }
}
