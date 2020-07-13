package Main.server.serverRequestProcessor;

import java.io.*;

public class DataRequestProcessor {
    public static String process(String[] splitRequest) {
        if (splitRequest[2].equals("allProducts")) {
            return allProductsResponse();
        } else if (splitRequest[2].equals("allCategories")) {
            return allCategoriesResponse();
        } else if (splitRequest[2].equals("allSellers")) {
            return allSellersResponse();
        }else if (splitRequest[2].equals("allOffs")) {
            return allOffsResponse();
        }
        return null;
    }

    private static String allOffsResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/offs.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private static String allSellersResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/sellers.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private static String allCategoriesResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/categories.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private static String allProductsResponse() {
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/JSON/products.json"));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "[]";
        }
    }


}
