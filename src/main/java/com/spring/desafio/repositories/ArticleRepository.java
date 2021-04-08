package com.spring.desafio.repositories;

import com.spring.desafio.entities.Article;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleRepository {

    /*
        Reads the excel file and retrieve all the cells value to map it to the Article java object.
     */
    public List<Article> getAllArticlesFromXlsx () throws Exception {
        FileInputStream file = new FileInputStream("src/main/resources/dbProductos.xlsx");
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        ArrayList<Article> articles = new ArrayList<>();
        for (Row row : sheet) {
            int index = 0;
            Article article = new Article();
            for (Cell cell : row) {
                if (cell.getRowIndex() != 0) {
                    switch (index) {
                        case 0: //ID
                            article.setProductId(String.valueOf((int)cell.getNumericCellValue()));
                            break;
                        case 1: //NAME
                            article.setName(cell.getRichStringCellValue().getString());
                            break;
                        case 2: //CATEGORY
                            article.setCategory(cell.getRichStringCellValue().getString());
                            break;
                        case 3: //BRAND
                            article.setBrand(cell.getRichStringCellValue().getString());
                            break;
                        case 4: //PRICE, Function to cast price to correct currency.
                            String price = cell.getRichStringCellValue().getString().substring(1).replace(".","");
                            String parsedPrice = currencyToBigDecimalFormat(price);
                            article.setPrice(Double.parseDouble(price));
                            break;
                        case 5: //QUANTITY
                            article.setQuantity((int) cell.getNumericCellValue());
                            break;
                        case 6: //FREESHIPPING
                            article.setFreeShipping(cell.getRichStringCellValue().getString().equals("SI"));
                            break;
                        case 7  : //PRESTIGE
                            article.setPrestige(cell.getRichStringCellValue().getString().length());
                            break;
                    }
                }
                index++;
            }
            if (article.getProductId() != null)
                articles.add(article);
        }
        return articles;
    }

    /*
    From a list of all the articles, make a filter by the map values, which are passed by the request.
     */
    public List<Article> getArticlesByFilters(Map<String, String> filters) throws Exception {
        ArrayList<Article> articles = (ArrayList<Article>) getAllArticlesFromXlsx();
        ArrayList<Article> filtered = new ArrayList<>();
        for (Article article : articles) {
            int match = 0;
            if (article.getName().equals(filters.get("name"))) match++;
            if (article.getCategory().equals(filters.get("category"))) match++;
            if (article.getBrand().equals(filters.get("brand"))) match++;
            if (filters.containsKey("price") && article.getPrice().equals(Double.parseDouble(currencyToBigDecimalFormat(filters.get("price"))))) match++;
            if (filters.containsKey("freeShipping") && filters.get("freeShipping").equals("true") && article.getFreeShipping()) match++;
            if (filters.containsKey("prestige") && article.getPrestige().equals(Integer.valueOf(filters.get("prestige")))) match++;
            if (filters.containsKey("order")) match += 1;
            if (match == filters.size()) filtered.add(article);
        }
        if (filters.containsKey("order")) setOrder(filtered,filters.get("order"));
        return filtered;
    }

    public Article getArticleById (String id) throws Exception {
        List<Article> allArticles = getAllArticlesFromXlsx();
        for (Article article : allArticles) {
            if (article.getProductId().equals(id)){
                return article;
            }
        }
        throw new NoSuchElementException("The product with id: " + id + " does not exist.");
    }

    private static void setOrder(ArrayList<Article> articles, String order) {
        switch (order) {
            case "0":
                articles.sort(Comparator.comparing(Article::getName));
            case "1":
                articles.sort(Comparator.comparing(Article::getName).reversed());
            case "2":
                articles.sort(Comparator.comparing(Article::getPrice).reversed());
            case "3":
                articles.sort(Comparator.comparing(Article::getPrice));
        }
    }

    /*
    This function is extracted from a source in the web, it manages the dot and comma as decimal separator to avoid
    the burden of parse prices from different regions.
     */
    private static String currencyToBigDecimalFormat(String currency) throws Exception {
        if(!doesMatch(currency))
            throw new Exception("Currency in wrong format " + currency);
        // Replace all dots with commas
        currency = currency.replaceAll("\\.", ",");
        // If fractions exist, the separator must be a .
        if(currency.length()>=3) {
            char[] chars = currency.toCharArray();
            if(chars[chars.length-2] == ',') {
                chars[chars.length-2] = '.';
            } else if(chars[chars.length-3] == ',') {
                chars[chars.length-3] = '.';
            }
            currency = new String(chars);
        }
        // Remove all commas
        return currency.replaceAll(",", "");
    }

    private static boolean doesMatch(String s) {
        try {
            Pattern pattern = Pattern.compile("^[+-]?[0-9]{1,3}(?:[0-9]*(?:[.,][0-9]{0,2})?|(?:,[0-9]{3})*(?:\\.[0-9]{0,2})?|(?:\\.[0-9]{3})*(?:,[0-9]{0,2})?)$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

}