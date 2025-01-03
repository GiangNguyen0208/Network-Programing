package rmi_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        try {
            Registry registry = LocateRegistry.getRegistry(7753);
            IUser userDAO = (IUser) registry.lookup("userDAO");
            IProduct productDAO = (IProduct) registry.lookup("productDAO");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("WELCOME TO MANAGE PRODUCT SYSTEM");
            
            String userInput = "";
            String username = "", password = "";
            String status = "";
            String sessionID = "";
            
            // Đăng nhập
            while (sessionID.isEmpty()) {
                System.out.print("Enter command (EXIT to quit): ");
                userInput = input.readLine();
                if ("EXIT".equalsIgnoreCase(userInput)) {
                    System.out.println("SYSTEM EXIT...");
                    return;
                }
                StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
                if (!tokenizer.hasMoreTokens()) {
                    System.out.println("Invalid command. Please try again.");
                    continue;
                }
                String cmd = tokenizer.nextToken().toUpperCase();
                String param = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
                
                switch (cmd) {
                    case "USER": {
                        if (param.isEmpty()) {
                            System.out.println("Username cannot be empty.");
                            break;
                        }
                        username = param;
                        status = userDAO.checkUsername(username) ? "OK" : "FALSE";
                        System.out.println("Username check: " + status);
                        break;
                    }
                    case "PASS": {
                        if (username.isEmpty()) {
                            System.out.println("Please enter a USER first.");
                            break;
                        }
                        if (param.isEmpty()) {
                            System.out.println("Password cannot be empty.");
                            break;
                        }
                        password = param;
                        boolean isLogin = userDAO.checkLogin(username, password);
                        sessionID = isLogin ? userDAO.createSession(username) : "";
                        status = isLogin ? "OK" : "FALSE";
                        System.out.println("Login status: " + status + ", WITH SESSIONID: " + sessionID);
                        break;
                    }
                    default:
                        System.out.println("Invalid command. Use USER or PASS.");
                        break;
                }
            }
            
            // Xử lý sau khi đăng nhập thành công
            while (!sessionID.isEmpty()) {
                System.out.print("Enter command (QUIT to logout): ");
                userInput = input.readLine();
                if ("QUIT".equalsIgnoreCase(userInput)) {
                    System.out.println("LOGOUT SUCCESSFUL!");
                    userDAO.logout(sessionID);
                    sessionID = "";
                    continue;
                }
                
                if (!userDAO.isValidSession(sessionID)) {
                	System.out.println("SessionID is inValid");
                	sessionID = "";
                } else {
                	System.out.println("SessionID is Valid");
                }
                
                StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
                if (!tokenizer.hasMoreTokens()) {
                    System.out.println("Invalid command. Please try again.");
                    continue;
                }
                String cmd = tokenizer.nextToken().toUpperCase();
                List<String> parameters = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    parameters.add(tokenizer.nextToken());
                }
                
                switch (cmd) {
                    case "ADD": {
                        if (parameters.size() < 4) {
                            System.out.println("Not enough parameters. Usage: ADD\tid\tname\tprice\tquantity");
                            break;
                        }
                        int id = Integer.parseInt(parameters.get(0));
                        String name = parameters.get(1);
                        double price = Double.parseDouble(parameters.get(2));
                        int quantity = Integer.parseInt(parameters.get(3));
                        
                        boolean isAdd = productDAO.add(new Product(id, name, price, quantity));
                        status = isAdd ? "OK" : "FALSE";
                        System.out.println("ADD status: " + status);
                        break;
                    }
                    case "EDIT": {
                        if (parameters.size() < 4) {
                            System.out.println("Not enough parameters. Usage: EDIT\tid\tname\tprice\tquantity");
                            break;
                        }
                        int id = Integer.parseInt(parameters.get(0));
                        String name = parameters.get(1);
                        double price = Double.parseDouble(parameters.get(2));
                        int quantity = Integer.parseInt(parameters.get(3));
                        boolean isEdit = productDAO.edit(new Product(id, name, price, quantity));
                        status = isEdit ? "OK" : "FALSE";
                        System.out.println("EDIT status: " + status);
                        break;
                    }
                    case "REMOVE": {
                        if (parameters.size() < 1) {
                            System.out.println("Not enough parameters. Usage: REMOVE\tid");
                            break;
                        }
                        int id = Integer.parseInt(parameters.get(0));
                        boolean isRemove = productDAO.remove(id);
                        status = isRemove ? "OK" : "FALSE";
                        System.out.println("REMOVE status: " + status);
                        break;
                    }
                    case "FBN": {
                        if (parameters.size() < 1) {
                            System.out.println("Not enough parameters. Usage: FBN\tname");
                            break;
                        }
                        String name = parameters.get(0);
                        List<Product> products = productDAO.findByName(name);
                        products.forEach(product -> System.out.println(product));
                        break;
                    }
                    case "FBID": {
                        if (parameters.size() < 1) {
                            System.out.println("Not enough parameters. Usage: FBID\tid");
                            break;
                        }
                        int id = Integer.parseInt(parameters.get(0));
                        Product product = productDAO.findByID(id);
                        System.out.println(product);
                        break;
                    }
                    case "VIEW": {
                        if (parameters.size() < 1) {
                            System.out.println("Not enough parameters. Usage: VIEW\ttableProducts");
                            break;
                        }
                        String name = parameters.get(0);
                        List<Product> products = productDAO.view(name);
                        products.forEach(product -> System.out.println(product));
                        break;
                    }
                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
