package com.onlineshop.shop.controllers;

import com.onlineshop.shop.models.Product;
import com.onlineshop.shop.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Optional;

public class HTMLPageCreator {

    public String createSearchPage(String value, String order, ProductRepository productRepository, int pageNumber) {

        StringBuilder HTML = new StringBuilder();
        ArrayList<Product> products = getProducts(value, order, productRepository);

        HTML.append( "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <title>Products</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <link rel=\"stylesheet\" href=\"css/main.css\"/>\n" +
                "        <script src=\"js/scripts.js\"></script>" +
                "    </head>\n" +
                "    <body style=\"margin:0;\">\n" +
                "        <div class=\"topbar\" id=\"topnav\" >\n" +
                "            <div class=\"topbaritem\"><a href=\"index\">Home</a></div>\n" +
                "            <div class=\"topbaritem\"><a href=\"sales\">Sales</a></div>\n" +
                "            <div class=\"topbaritem\"><a href=\"/search?value=\" class=\"active\">Products</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"float: right;\"><a href=\"settings\">Settings</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"float: right;\"><a href=\"account\">Account</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"margin: auto;width: 45%; float: right;\">\n" +
                "                <div id=\"searchbar\">\n" +
                "                    <form action=\"/search\" style=\"margin-block-end: 0em;\">\n" +
                "                        <input  id=\"search_submit\" type=\"submit\" value=\"Search\">\n" +
                "                        <input id=\"search_input\" type=\"text\" name=\"value\" >\n" +
                "                    </form>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"result_legend\">\n");


        if (value.equals(""))
            HTML.append("<h1 id=\"result\">All products: </h1>\n");
        else
            HTML.append("<h1 id=\"result\">Search results for \"" + value + "\"</h1>\n");

        String form  = getForm(order, value, products.size(), pageNumber);

        HTML.append(form);
        HTML.append("        <div class=\"item_container\">");
        HTML.append(getItemsDivs(products, pageNumber));
        HTML.append(form);
        HTML.append("   <div class=\"bottom\" id=\"bottomid\">\n" +
                "            <h3>Contact:</h3>\n" +
                "                This is not a working site, it was created only in learning purpouses <br>\n" +
                "                My buissness mail: m.pilch2002@gmail.com<br><br>\n" +
                "            <input type=\"button\" onclick=\"closeBottom()\" value=\"Close\"/>" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>");

        return HTML.toString();
    }

    private String getForm(String order, String value, int size, int pageNumber) {
        String[] selected = new String[5];
        for (int i = 0; i < 5; i++) {
            selected[i] = "";
        }

        switch (order) {
            case "pop": selected[0] = "selected=\"selected\"";
                break;
            case "pasc" : selected[1] = "selected=\"selected\"";
                break;
            case "pdesc" : selected[2] = "selected=\"selected\"";
                break;
            case "new" : selected[3] = "selected=\"selected\"";
                break;
            case "old" : selected[4] = "selected=\"selected\"";
                break;
        }
        String form  = "     </div>\n" +
                "        <div class=\"filters\">\n" +
                "            <form action=\"/search\">\n" +
                "                <input type=\"text\" value=\"" + value + "\" name=\"value\" style=\"visibility: hidden; width: 0;\"/>\n" +
                "                <label>Order by</label>\n" +
                "                <select name=\"order\">\n" +
                "                    <option value=\"pop\"" + selected[0] +">Popularity</option>\n" +
                "                    <option value=\"pasc\"" + selected[1] + ">Price ascending</option>\n" +
                "                    <option value=\"pdesc\"" + selected[2] + ">Price descending</option>\n" +
                "                    <option value=\"new\"" + selected[3] + ">Newest</option>\n" +
                "                    <option value=\"old\"" + selected[4] + ">Oldest</option>\n" +
                "                </select>\n" +
                "                \n" +
                "                <label>Page</label>" +
                "                <select name=\"page\">";

        for (int i = 0; i < (size/ 30) + 1; i++) {
            if (i + 1 == pageNumber)
                form += String.format("<option selected=\"selected\" value=%d>%d</option>", i + 1, i + 1);
            else
                form += String.format("<option value=%d>%d</option>", i + 1, i + 1);
        }

        form += "            </select>" +
                "                <input type=\"submit\" value=\"search\"/>\n" +
                "            </form>\n" +
                "        </div>";

        return form;
    }

    private String getItemsDivs(ArrayList<Product> products, int pageNumber) {
        StringBuilder HTML = new StringBuilder();
        for (int i = 0 + 30 * (pageNumber - 1); i < 30 + 30 * (pageNumber - 1); i++) {
            if (products.size() <= i)
                break;
            else {
                Product product = products.get(i);
                HTML.append("<div class=\"item\">" +
                        "    <img class=\"img\" src=\"img\\" + product.getID() + ".jpg\"/><br>" +
                        "    <a class=\"link_product_name\" href=\"product?id=" + product.getID() +"\">" +
                        product.getProductName() + "</a><br>" +
                        "    <a class=\"link_brand\" href=\"\">" + product.getBrand() + "</a><br>");
                HTML.append(getPrice(product));
                HTML.append("</div>");
            }
        }
        return HTML.toString();
    }

    private String getPrice(Product product) {
        if (product.getOnSale() == 0) {
            return "<p class=\"price\">" + String.format("%.2f$", product.getPrice()) + "</p>";
        }
        else {
            String div = "<p class=\"old_price\">" + String.format("%.2f$", product.getPrice())  + "</p>";
            String newPrice = String.format("%.2f$", product.getPrice() * (1 - product.getOnSale() / 100.));
            div += "<p class=\"new_price\">" + newPrice  + "</p>";
            div += "<p class=\"sales_percent\">" + (int) product.getOnSale() + "%</p>";
            return div;
        }
    }

    private ArrayList<Product> getArrayListFormIDs(Iterable<Integer> productsIDs, ProductRepository productRepository) {
        ArrayList<Product> result = new ArrayList<>();
        productsIDs.forEach(e -> {
            Optional<Product> product = productRepository.findById(e);
            if (product.isPresent())
                result.add(product.get());
        });

        return result;
    }

    private ArrayList<Product> getProducts(String value,String order, ProductRepository productRepository) {
        ArrayList<Product> products;
        if (order != null) {
            Iterable<Integer> productsIDs;
            switch (order) {
                case "pop" : productsIDs = productRepository.searchProductsPopularity("%" + value + "%");
                    break;
                case "pasc" : productsIDs = productRepository.searchPriceAsc("%" + value + "%");
                    break;
                case "pdesc": productsIDs = productRepository.searchPriceDesc("%" + value + "%");
                    break;
                case "new" : productsIDs = productRepository.searchDateDesc("%" + value + "%");
                    break;
                case "old" : productsIDs = productRepository.searchDateAsc("%" + value + "%");
                    break;
                default: productsIDs = productRepository.searchProducts("%" + value + "%");
            }
            products = getArrayListFormIDs(productsIDs, productRepository);
        }
        else {
            var productsIDs = productRepository.searchProductsPopularity("%" + value + "%");
            products = getArrayListFormIDs(productsIDs, productRepository);
        }

        return products;
    }


    public String createProductPage(int ID, ProductRepository productRepository) {
        String HTML = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <title>Product</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <link rel=\"stylesheet\" href=\"css/main.css\"/>\n" +
                "        <script src=\"js/scripts.js\"></script>\n" +
                "    </head>\n" +
                "    <body style=\"margin:0;\">\n" +
                "        <div class=\"topbar\" id=\"topnav\" >\n" +
                "            <div class=\"topbaritem\"><a href=\"index\">Home</a></div>\n" +
                "            <div class=\"topbaritem\"><a href=\"sales\">Sales</a></div>\n" +
                "            <div class=\"topbaritem\"><a href=\"/search?value=\" class=\"active\">Products</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"float: right;\"><a href=\"settings\" >Settings</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"float: right;\"><a href=\"account\">Account</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"margin: auto;width: 45%; float: right;\">\n" +
                "                <div id=\"searchbar\">\n" +
                "                    <form action=\"/search\">\n" +
                "                        <input  id=\"search_submit\" type=\"submit\" value=\"Search\">\n" +
                "                        <input id=\"search_input\" type=\"text\" name=\"value\">\n" +
                "                    </form>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div id=\"product\" style=\"margin: 1% 20%;\">\n" +
                "            <div id=\"product_images\">\n";

        HTML += getIMGs(ID);

        HTML += "            </div>\n" + getProductBio(ID, productRepository) +
                "        </div>\n" +
                "        <div class=\"bottom\" id=\"bottomid\">\n" +
                "            <h3>Contact:</h3>\n" +
                "                This is not a working site, it was created only in learning purpouses <br>\n" +
                "                My buissness mail: m.pilch2002@gmail.com<br><br>\n" +
                "            <input type=\"button\" onclick=\"closeBottom()\" value=\"Close\"/>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";


        return HTML;
    }

    private String getIMGs(int ID) {
        String HTML = "<img id=\"big_image\" src=\"img/products/" + ID + "/" + ID +"_1.jpg\" alt=\"----\"/>\n" +
                "      <br>\n" +
                "      <div id=\"jpg_buttons\">\n" +
                "           <button class=\"small_img_button\" onclick=\"imgButton('img/products/" + ID + "/" + ID + "_1.jpg')\">" +
                "               <img src=\"img/products/" + ID + "/" + ID + "_1.jpg\" class=\"small_img\">" +
                "           </button>\n" +
                "           <button class=\"small_img_button\" onclick=\"imgButton('img/products/" + ID + "/" + ID + "_2.jpg')\">" +
                "               <img src=\"img/products/" + ID + "/" + ID + "_2.jpg\" class=\"small_img\">" +
                "           </button>\n" +
                "           <button class=\"small_img_button\" onclick=\"imgButton('img/products/" + ID + "/" + ID + "_3.jpg')\">" +
                "               <img src=\"img/products/" + ID + "/" + ID + "_3.jpg\" class=\"small_img\">" +
                "           </button>\n" +
                "           <button class=\"small_img_button\" onclick=\"imgButton('img/products/" + ID + "/" + ID + "_4.jpg')\">" +
                "               <img src=\"img/products/" + ID + "/" + ID + "_4.jpg\" class=\"small_img\">" +
                "           </button>\n" +
                "           <button class=\"small_img_button\" onclick=\"imgButton('img/products/" + ID + "/" + ID + "_5.jpg')\">" +
                "               <img src=\"img/products/" + ID + "/" + ID + "_5.jpg\" class=\"small_img\">" +
                "           </button>\n" +
                "      </div>\n";

        return HTML;
    }

    private String getProductBio(int id, ProductRepository productRepository) {
        var product = productRepository.findById(id).get();
        String HTML = " <div id=\"product_bio\">\n" +
                "                <h1>" + product.getProductName() +"</h1>\n" +
                "                <a>" + product.getBrand()+"</a>\n";
        HTML += getPrice(product);
        HTML += "                <h3 style=\"text-align: left;\">" + product.getDescription() + "</h3>\n" +
                "            </div>";
        return HTML;
    }

    public String createSalesPage(ProductRepository productRepository) {
        StringBuilder HTML = new StringBuilder();
        HTML.append("<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <title>Products</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <link rel=\"stylesheet\" href=\"css/main.css\"/>\n" +
                "        <script src=\"js/scripts.js\"></script>" +
                "    </head>\n" +
                "    <body style=\"margin:0;\">\n" +
                "        <div class=\"topbar\" id=\"topnav\" >\n" +
                "            <div class=\"topbaritem\"><a href=\"index\">Home</a></div>\n" +
                "            <div class=\"topbaritem\"><a href=\"sales\" class=\"active\">Sales</a></div>\n" +
                "            <div class=\"topbaritem\"><a href=\"/search?value=\">Products</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"float: right;\"><a href=\"settings\">Settings</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"float: right;\"><a href=\"account\">Account</a></div>\n" +
                "            <div class=\"topbaritem\" style=\"margin: auto;width: 45%; float: right;\">\n" +
                "                <div id=\"searchbar\">\n" +
                "                    <form action=\"/search\" style=\"margin-block-end: 0em;\">\n" +
                "                        <input  id=\"search_submit\" type=\"submit\" value=\"Search\">\n" +
                "                        <input id=\"search_input\" type=\"text\" name=\"value\" >\n" +
                "                    </form>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"result_legend\">\n");

        HTML.append("<h1 id=\"result\">Sales: </h1>\n");
        HTML.append(getItemsDivs(getArrayListFormIDs(productRepository.searchOnSale(), productRepository), 1));
        return HTML.toString();
    }


}
