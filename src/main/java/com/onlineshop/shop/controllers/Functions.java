package com.onlineshop.shop.controllers;

import com.onlineshop.shop.models.Product;
import com.onlineshop.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class Functions {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/settings")
    public String getSettingsPage() {
        return "settings";
    }

    @GetMapping("/account")
    public String getAccountPage() {
        return "account";
    }

    @GetMapping("/products")
    public String getProductsPage() {
        return "products";
    }

    /*
    @GetMapping("/search")
    public String getSearchPage() {
        return "search";
    }
    */

    @GetMapping("/sales")
    public String getSalesPage() {
        return "sales";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/searchtemp")
    public  @ResponseBody Iterable<Product> getSearchedProductsTemp(@RequestParam String value) {
        var productsIDs = productRepository.searchProducts("%" + value  + "%");
        ArrayList<Product> products = new ArrayList<>();
        productsIDs.forEach(e -> {
            var product = productRepository.findById(e);
            if (product.isPresent())
                products.add(product.get());
        });

        return products;
    }

    @GetMapping("/search")
    public @ResponseBody String getSearchedProducts(@RequestParam String value) {
        StringBuilder HTML = new StringBuilder();
        ArrayList<Product> products = new ArrayList<>();
        if (!value.equals("")){
            var productsIDs = productRepository.searchProducts("%" + value  + "%");
            productsIDs.forEach(e -> {
                var product = productRepository.findById(e);
                if (product.isPresent())
                    products.add(product.get());
            });
        }
        else {
            var productsIterable = productRepository.findAll();
            productsIterable.forEach(products::add);
        }

        HTML.append( "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <title>Home</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <link rel=\"stylesheet\" href=\"css/main.css\"/>\n" +
                "    </head>\n" +
                "    <body style=\"margin:0;\">\n" +
                "        <div class=\"topbar\" id=\"topnav\" >\n" +
                "            <div class=\"topbaritem\"><a href=\"\">Home</a></div>\n" +
                "            <div class=\"topbaritem\"><a href=\"sales\">Sales</a></div>\n" +
                "            <div class=\"topbaritem\"><a href=\"products\" class=\"active\">Products</a></div>\n" +
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
                "        <div class=\"filters\">" +
                "        </div>" +
                "        <div class=\"item_container\">");

        for (int j = 0; j < 10; j++)
        for (int i = 0; i < 30; i++) {
            if (products.size() <= i)
                break;
            else {
                Product product = products.get(i);
                HTML.append("<div class=\"item\">" +
                        "    <img class=\"img\" src=\"img\\" + product.getID() + ".jpg\"/><br>" +
                        "    <a class=\"link_product_name\" href=\"\">" + product.getProductName() + "</a><br>" +
                        "    <a class=\"link_brand\" href=\"\">" + product.getBrand() + "</a><br>" +
                        "    <p class=\"price\">" + product.getPrice() + "0$</p>" +
                        "</div>");
            }
        }

        HTML.append( "   </div>" +
                "       <div class=\"bottom\">\n" +
                "            <h3>Contact:</h3>\n" +
                "                This is not a working site, it was created only in learning purpouses <br>\n" +
                "                My buissness mail: m.pilch2002@gmail.com<br><br>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>");

        return HTML.toString();
    }
}
