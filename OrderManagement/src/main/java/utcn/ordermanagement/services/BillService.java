package utcn.ordermanagement.services;

import utcn.ordermanagement.models.Bill;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillService {
    private static String path = "D:\\MyWorkSpace\\MyProjects\\OrderManagement\\OrderManagement\\src\\main\\resources";
    private static String style = "table {\n" +
            "            margin: 0 auto;\n" +
            "            border-collapse: collapse;\n" +
            "        }\n" +
            "        th, td {\n" +
            "            padding: 8px;\n" +
            "            border: 1px solid #ddd;\n" +
            "            text-align: left;\n" +
            "        }\n" +
            "        th {\n" +
            "            background-color: #f2f2f2;\n" +
            "        }";
    public static void generateBill(Bill bill) {
        try (FileWriter writer = new FileWriter(path + "bill-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".html")) {
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>Bill Information</title>\n");
            writer.write("<style>\n");
            writer.write(style + "\n");
            writer.write("</style>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("<table border=\"1\">\n");
            writer.write("<tr><th>Name</th><th>Total Price</th><th>Client</th><th>Product</th><th>Emitted</th></tr>\n");
            writer.write("<tr>\n");
            writer.write("<td>" + "Bill " + bill.placedAt().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "</td>\n");
            writer.write("<td>" + bill.totalPrice() + "</td>\n");
            writer.write("<td>" + bill.clientName() + "</td>\n");
            writer.write("<td>" + bill.productName() + "</td>\n");
            writer.write("<td>" + bill.placedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td>\n");
            writer.write("</tr>\n");
            writer.write("</table>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
