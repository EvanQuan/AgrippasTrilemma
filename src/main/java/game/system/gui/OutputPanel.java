package game.system.gui;

import game.system.output.IPrintBuffer;
import game.system.output.SemanticColor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;
import java.awt.*;

/**
 * Output of game text Has a scroll pane
 */
public class OutputPanel extends GridBagPanel implements IPrintBuffer {

    public static final int TEXT_AREA_HEIGHT = 0;
    public static final int TEXT_AREA_WIDTH = 0;
    public static final int TEXT_X = 0;
    public static final int TEXT_Y = 0;
    public static final int TEXT_WIDTH = 1;
    public static final int TEXT_HEIGHT = 1;
    // Default font
    public static final String DEFAULT_NAME = "Consolas";
    public static final int DEFAULT_STYLE = Font.PLAIN;
    public static final int DEFAULT_SIZE = 16;
    // Player receiveInput font
    public static final String INPUT_NAME = "Consolas";
    public static final int INPUT_STYLE = Font.BOLD;
    public static final int INPUT_SIZE = DEFAULT_SIZE;
    // Special font
    public static final String SPECIAL_NAME = "Consolas";
    public static final int SPECIAL_STYLE = Font.BOLD;
    public static final int SPECIAL_SIZE = DEFAULT_SIZE;
    public static final int SPEECH_STYLE = Font.ITALIC;
    public static final int DIRECTION_STYLE = Font.BOLD;
    // Border font
    public static final String BORDER_NAME = "SansSerif";
    public static final int BORDER_STYLE = Font.BOLD;
    public static final int BORDER_SIZE = 24;
    public static final Color BORDER_COLOR = Color.RED;
    // Title font
    public static final String TITLE_NAME = "Consolas";
    public static final int TITLE_STYLE = Font.BOLD;
    public static final int TITLE_SIZE = DEFAULT_SIZE + 2;
    private static final long serialVersionUID = 1L;

    // Speech font
    private static OutputPanel outputPanel = new OutputPanel();
    private JTextPane textPane;
    // private JTextArea textArea;
    private JScrollPane scrollPane;
    private TitledBorder border;
    private Font defaultFont;
    private Font inputFont;
    private Font specialFont;
    private Font borderFont;
    private Font titleFont;
    private Font speechFont;
    private Font directionFont;

    /**
     * Private constructor.
     */
    private OutputPanel() {
        initFont();
        initTextPane();
        // initBorder(); // text pane does not have a border?
    }

    /**
     * @return singleton instance of OutputPanel
     */
    public static OutputPanel getInstance() {
        return outputPanel;
    }

    /**
     * Returns value of outputPanel
     *
     * @return
     */
    public static OutputPanel getOutputPanel() {
        return outputPanel;
    }

    /**
     * Sets new value of outputPanel
     *
     * @param
     */
    public static void setOutputPanel(OutputPanel outputPanel) {
        OutputPanel.outputPanel = outputPanel;
    }
    // private void initTextArea() {
    //     textArea = new JTextArea(TEXT_AREA_HEIGHT,TEXT_AREA_WIDTH);
    //     textArea.setEditable(false);
    //     textArea.setLineWrap(true);
    //     textArea.setWrapStyleWord(true);
    //     textArea.setFont(font);
    //     scrollPane = new JScrollPane(textArea);
    //     setFill(GridBagConstraints.BOTH);
    //     setWeightX(1.0);
    //     setWeightY(1.0);
    //     addComponent(scrollPane,TEXT_X,TEXT_Y,TEXT_WIDTH,TEXT_HEIGHT);
    // }

    /**
     * Initialize the fonts used in the text pane.
     */
    private void initFont() {
        defaultFont = new Font(DEFAULT_NAME, DEFAULT_STYLE, DEFAULT_SIZE);
        inputFont = new Font(INPUT_NAME, INPUT_STYLE, INPUT_SIZE);
        specialFont = new Font(SPECIAL_NAME, SPECIAL_STYLE, SPECIAL_SIZE);
        borderFont = new Font(BORDER_NAME, BORDER_STYLE, BORDER_SIZE);
        titleFont = new Font(TITLE_NAME, TITLE_STYLE, TITLE_SIZE);
        speechFont = new Font(SPECIAL_NAME, SPEECH_STYLE, SPECIAL_SIZE);
        directionFont = new Font(SPECIAL_NAME, DIRECTION_STYLE, SPECIAL_SIZE);
    }


    /**
     * Append text to textArea
     * @param String s to output
     */
    // public void print(String s) {
    //     textArea.setText(textArea.getText() + s);
    // }

    /**
     * Initialize all the GUI components of the output panel. Currently composed
     * of a text pane, and scroll pane.
     */
    private void initTextPane() {
        textPane = new JTextPane();
        textPane.setEditable(false);
        scrollPane = new JScrollPane(textPane);
        setFill(GridBagConstraints.BOTH);
        setWeightX(1.0);
        setWeightY(1.0);
        addComponent(scrollPane, TEXT_X, TEXT_Y, TEXT_WIDTH, TEXT_HEIGHT);
    }

    private void initBorder() {
        border = new TitledBorder("Main Menu");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleFont(borderFont);
        border.setTitleColor(BORDER_COLOR);
        setBorder(border);
    }

    /**
     * Append text to textPane with given font and color
     *
     * @param string to print to outputPanel
     * @param font   of string
     * @param color  of string
     */
    public void print(String string, Font font, SemanticColor color) {
        StyledDocument doc = textPane.getStyledDocument();


        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground,
                SemanticColor.toGUIColor(color));

        attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.FontFamily, font.getName());
        attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.FontSize, font.getSize());
        attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Bold, font.isBold());
        attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Italic, font.isItalic());

        // if (font.isBold()) {
        //     StyleConstants.setBold(attributeSet, true);
        // } else if (font.isItalic()) {
        //     StyleConstants.setItalic(attributeSet, true);
        // }

        int length = textPane.getDocument().getLength();
        textPane.setCaretPosition(length);
        textPane.setCharacterAttributes(attributeSet, false);
        // textPane.replaceSelection(string);

        // Print all output to command line
        // System.out.println(string);

        // debug
        // System.out.println(attributeSet);

        try {
            doc.insertString(length, string, attributeSet);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    /**
     * Append text of default font and color to output panel.
     *
     * @param output
     */
    public void print(String output) {
        print(output, defaultFont, SemanticColor.DEFAULT);
    }

    /**
     * Append text of default font and specified color to output panel.
     *
     * @param output
     * @param semanticColor
     */
    public void print(String output, SemanticColor semanticColor) {
        print(output, defaultFont, semanticColor);
    }

    /**
     * @param output to print to output buffer in default color.
     */
    @Override
    public void print(double output) {
        print(Double.toString(output));
    }

    /**
     * @param output        to print to output buffer
     * @param semanticColor of output
     */
    @Override
    public void print(double output, SemanticColor semanticColor) {
        print(Double.toString(output), semanticColor);
    }

    /**
     * @param output to print to output buffer in default color.
     */
    @Override
    public void print(int output) {
        print(Integer.toString(output));
    }

    /**
     * @param output        to print to output buffer
     * @param semanticColor of output
     */
    @Override
    public void print(int output, SemanticColor semanticColor) {
        print(Integer.toString(output), semanticColor);
    }

    /**
     * @param output to print to output buffer in default color.
     */
    @Override
    public void print(Object output) {
        print(output.toString());
    }

    /**
     * @param output        to print to output buffer
     * @param semanticColor of output
     */
    @Override
    public void print(Object output, SemanticColor semanticColor) {
        print(output.toString(), semanticColor);
    }

    /**
     * @param output to print to output buffer in default color.
     */
    @Override
    public void println(double output) {
        println(Double.toString(output));
    }

    /**
     * @param output        to print to output buffer
     * @param semanticColor of output
     */
    @Override
    public void println(double output, SemanticColor semanticColor) {
        println(Double.toString(output), semanticColor);
    }

    /**
     * @param output to print to output buffer in default color.
     */
    @Override
    public void println(int output) {
        println(Integer.toString(output));
    }

    /**
     * @param output        to print to output buffer
     * @param semanticColor of output
     */
    @Override
    public void println(int output, SemanticColor semanticColor) {
        println(Integer.toString(output), semanticColor);
    }

    @Override
    public void println(Object output) {
        println(output.toString());
    }

    /**
     * @param output        to print to output buffer
     * @param semanticColor of output
     */
    @Override
    public void println(Object output, SemanticColor semanticColor) {
        println(output.toString(), semanticColor);
    }

    /**
     * @param output to print to output buffer in default color.
     */
    @Override
    public void println(String output) {
        println(output, SemanticColor.DEFAULT);
    }

    /**
     * @param output        to print to output buffer
     * @param semanticColor of output
     */
    @Override
    public void println(String output, SemanticColor semanticColor) {
        print(output + System.lineSeparator(), semanticColor);
    }

    /**
     * Utility method for setting the font and color of a JTextPane. The
     * result is roughly equivalent to calling setFont(...) and
     * setForeground(...) on an AWT TextArea.
     *
     * Source
     * http://javatechniques.com/blog/setting-jtextpane-font-and-color/
     */
    // public void setJTextPaneFont(Font font, SemanticColor c) {
    //     // Start with the current receiveInput attributes for the JTextPane. This
    //     // should ensure that we do not wipe out any existing attributes
    //     // (such as alignment or other paragraph attributes) currently
    //     // set on the text area.
    //     MutableAttributeSet attrs = this.textPane.getInputAttributes();
    //
    //     // Set the font family, size, and style, based on properties of
    //     // the Font object. Note that JTextPane supports a number of
    //     // character attributes beyond those supported by the Font class.
    //     // For example, underline, strike-through, super- and sub-script.
    //     StyleConstants.setFontFamily(attrs, font.getFamily());
    //     StyleConstants.setFontSize(attrs, font.getSize());
    //     StyleConstants.setItalic(attrs, (font.getStyle() & Font.ITALIC) != 0);
    //     StyleConstants.setBold(attrs, (font.getStyle() & Font.BOLD) != 0);
    //
    //     // Set the font color
    //     StyleConstants.setForeground(attrs, c);
    //
    //     // Retrieve the pane's document object
    //     StyledDocument doc = this.textPane.getStyledDocument();
    //
    //     // Replace the style for the entire document. We exceed the length
    //     // of the document by 1 so that text entered at the end of the
    //     // document uses the attributes.
    //     doc.setCharacterAttributes(0, doc.getLength() + 1, attrs, false);
    // }

    /**
     * Append a number of System.lineseparator() to output buffer.
     *
     * @param lines to print
     */
    @Override
    public void printlns(int lines) {
        for (int i = 0; i < lines; i++) {
            println();
        }
    }

    /**
     * Append System.lineseparator to output buffer.
     */
    @Override
    public void println() {
        print(System.lineSeparator());
    }

    /**
     * Returns value of textPane
     *
     * @return
     */
    public JTextPane getTextPane() {
        return this.textPane;
    }

    /**
     * Sets new value of textPane
     *
     * @param
     */
    public void setTextPane(JTextPane textPane) {
        this.textPane = textPane;
    }

    /**
     * Returns value of scrollPane
     *
     * @return
     */
    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    /**
     * Sets new value of scrollPane
     *
     * @param
     */
    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    /**
     * Returns value of border
     *
     * @return
     */
    public TitledBorder getBorder() {
        return this.border;
    }

    /**
     * Sets new value of border
     *
     * @param
     */
    public void setBorder(TitledBorder border) {
        this.border = border;
    }

    /**
     * Returns value of defaultFont
     *
     * @return
     */
    public Font getDefaultFont() {
        return this.defaultFont;
    }

    /**
     * Sets new value of defaultFont
     *
     * @param
     */
    public void setDefaultFont(Font defaultFont) {
        this.defaultFont = defaultFont;
    }

    /**
     * Returns value of inputFont
     *
     * @return
     */
    public Font getInputFont() {
        return this.inputFont;
    }

    /**
     * Sets new value of inputFont
     *
     * @param
     */
    public void setInputFont(Font inputFont) {
        this.inputFont = inputFont;
    }

    /**
     * Returns value of specialFont
     *
     * @return
     */
    public Font getSpecialFont() {
        return this.specialFont;
    }

    /**
     * Sets new value of specialFont
     *
     * @param
     */
    public void setSpecialFont(Font specialFont) {
        this.specialFont = specialFont;
    }

    /**
     * Returns value of borderFont
     *
     * @return
     */
    public Font getBorderFont() {
        return this.borderFont;
    }

    /**
     * Sets new value of borderFont
     *
     * @param
     */
    public void setBorderFont(Font borderFont) {
        this.borderFont = borderFont;
    }
}
