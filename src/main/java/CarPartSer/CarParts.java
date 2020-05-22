package CarPartSer;

/**
 *
 * @author 217164692
 */

import java.io.*;
public class CarParts implements Serializable
{
    private int catalogNum;
    private String description;
    private double purchPrice;
    private double sellingPrice;
    private int quantitySold;
    private boolean inStock;


    public CarParts()    {

    }

    public CarParts(int catNum, String descr, double purchPr, double sellPr, int quant, boolean inStk)    {
        setCatNumber(catNum);
        setDescription(descr);
        setPurchPrice(purchPr);
        setSellingPrice(sellPr);
        setQuantitySold(quant);
        setInStock(inStk);
    }

    public void setCatNumber(int catNumber)  {
        this.catalogNum = catNumber;
    }
    public void setDescription(String descr)
    {
        this.description = descr;
    }

    public void setPurchPrice(double purchPr)   {
        this.purchPrice = purchPr;
    }
    public void setSellingPrice(double sellPr)   {
        this.sellingPrice = sellPr;
    }

    public void setQuantitySold(int quant)    {
        this.quantitySold = quant;
    }

    public void setInStock(boolean inStk)  {
        this.inStock = inStk;
    }

    public int getCatNumber()  {
        return catalogNum;
    }

    public String getDescription()     {
        return description;
    }

    public double getPurchPrice()    {
        return purchPrice;
    }

    public double getSellingPrice()    {
        return sellingPrice;
    }

    public int getQuantitySold()    {
        return quantitySold;
    }

    public boolean getInStock()     {
        return inStock;
    }

    public String toString()      {
        return String.format("%-15d\t%-15s\t%-8.2f\t%-8.2f\t%-6d\t%-8s", getCatNumber(), getDescription(), getPurchPrice(),
                getSellingPrice(), getQuantitySold(), new Boolean(getInStock()).toString());
    }
}