package EPOS;

/**
 * @date:   Mar 13, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary
 * @package: EPOS
 * @file name: DoctorLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class TestCatMapLine 
{
    private int TestNumber;
    private int SubtestNumber;
    private int CategoryNumber;
    private int Position;
    private int Indent;
    private String Category;
    private String TestName;
    private String Metabolite;
    private String Text;


    public TestCatMapLine()
    {
    }

    public TestCatMapLine(int TestNumber, int SubtestNumber, int CategoryNumber, int Position, int Indent, String Category, String TestName, String Metabolite, String Text)
    {
        this.TestNumber = TestNumber;
        this.SubtestNumber = SubtestNumber;
        this.CategoryNumber = CategoryNumber;
        this.Position = Position;
        this.Indent = Indent;
        this.Category = Category;
        this.TestName = TestName;
        this.Metabolite = Metabolite;
        this.Text = Text;
    }

    public int getTestNumber()
    {
        return TestNumber;
    }

    public void setTestNumber(int TestNumber)
    {
        this.TestNumber = TestNumber;
    }

    public int getSubtestNumber()
    {
        return SubtestNumber;
    }

    public void setSubtestNumber(int SubtestNumber)
    {
        this.SubtestNumber = SubtestNumber;
    }

    public int getCategoryNumber()
    {
        return CategoryNumber;
    }

    public void setCategoryNumber(int CategoryNumber)
    {
        this.CategoryNumber = CategoryNumber;
    }

    public int getPosition()
    {
        return Position;
    }

    public void setPosition(int Position)
    {
        this.Position = Position;
    }

    public int getIndent()
    {
        return Indent;
    }

    public void setIndent(int Indent)
    {
        this.Indent = Indent;
    }

    public String getCategory()
    {
        return Category;
    }

    public void setCategory(String Category)
    {
        this.Category = Category;
    }

    public String getTestName()
    {
        return TestName;
    }

    public void setTestName(String TestName)
    {
        this.TestName = TestName;
    }

    public String getMetabolite()
    {
        return Metabolite;
    }

    public void setMetabolite(String Metabolite)
    {
        this.Metabolite = Metabolite;
    }

    public String getText()
    {
        return Text;
    }

    public void setText(String Text)
    {
        this.Text = Text;
    }
    
    
}
