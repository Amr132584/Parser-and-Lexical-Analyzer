/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Amr
 */
public class Tokenize {

    int token_num = 0;
    public int line_num;
    public String type;
    public String token;
    public ArrayList<Tokenize> all = new ArrayList();
    Tokenize tok;
public int all_lines;
    class reserved_words {

        public String[] words = {"program", "if", "while", "then", "do", "end", "begin", "var", "function", "procedure", "not", "array","else"};
        public String[] OUtput = {"PROGRAM", "IF", "WHILE", "THEN", "DO", "END", "BEGIN", "VAR", "FUNCTION", "PROCEDURE", "NOT", "ARRAY","ELSE"};
    }

    class operators {

        public char[] ops = {'+', '-', '*', '/', '<', '>', '!', '='};
        public String[] type = {"ADDOP", "ADDOP", "MULOP", "MULOP", "RELOP", "RELOP", "RELOP", "ASSIGNOP"};
    }
    char[] Symbols = {'{', '}', ']', '[', '(', ')', ',', ';', '.',':'};
    String[] Data_types = {"float", "integer"};
    //Struct lines 
    StringBuilder sss = new StringBuilder();

    public ArrayList<char[]> read_code() {

        ArrayList<char[]> lines = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("code.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line.toCharArray());

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return lines;
    }

    public void tokenize() {
        ArrayList<char[]> lines = read_code();
        char[] line;
        System.out.println("line Number:\tToken:\t\tToken Type:");

        for (int i = 0; i < lines.size(); i++) {
            line = new char[lines.get(i).length];
            line = lines.get(i);
            line = remove_comments(line);
            Determine_types(line, i + 1);
        }

    }

    public void Determine_types(char[] line, int line_num) {
        Tokenize tok = new Tokenize();;
        boolean has_num = false;
        sss = new StringBuilder();
        StringBuilder lit = new StringBuilder();
        int state = 0;

        for (int i = 0; i < line.length; i++) {
            /*
               the next block of code removes all the spaces from the temporary used stringbuilder sss
           
             */

            int j = 0;
            for (int m = 0; m < sss.length(); m++) {// m loops untill finding space
                if (!Character.isWhitespace(sss.charAt(m))) {
                    sss.setCharAt(j++, sss.charAt(m));
                }
            }
            sss.delete(j, sss.length());// when space found it is deleted 

            /*
          ***end of spaces removal***
          
        The follwoing code check if the word is Identifier, it is a DFA with 3 states consist of 4 if conditions
        to check the current state and the input

             */
            all_lines = line_num;
            int stat = 0;
            if (Character.isAlphabetic(line[i]) && sss.length() == 0) {
                sss.append(line[i]);
                stat = i;
            } else if (Character.isAlphabetic(line[i]) && i + 1 != line.length || (line[i] == '_' && sss.length() != 0) || Character.isDigit(line[i]) && sss.length() != 0 && this.isNumeric(sss.toString()) == false) {

                sss.append(line[i]);
                if (Character.isDigit(line[i])) {
                    has_num = true;
                }

            }

            if (i + 1 == line.length && sss.length() != 0 || sss.length() != 0 && !Character.isAlphabetic(line[i]) && !Character.isDigit(line[i]) && this.isNumeric(sss.toString()) == false) {
                if (has_num == true ||is_datatype(sss.toString(), line_num)==false&& is_reserved(sss.toString(), line_num) == false && this.is_datatype(sss.toString(), line_num) == false && this.isNumeric(sss.toString()) == false) {
                    if (i + 1 < line.length) {
                        if (this.Check_alpha_or_digit(line[i + 1]) && this.Check_alpha_or_digit(line[i]) && !this.isNumeric(sss.toString())) {
                            continue;
                        }
                    }
                    if (Character.isAlphabetic(line[i]) && i + 1 == line.length && stat != i) {
                        sss.append(line[i]);
                    } 
                    
                    if (!this.is_reserved(sss.toString(), line_num)  && ! this.is_datatype(sss.toString(), line_num)) {
                        tok = new Tokenize();
                        tok.line_num = line_num;
                        tok.token = sss.toString();

                        tok.type = "IDENTIFIER";

                        has_num = false;
                        sss = new StringBuilder();
                        stat = 0;
                        all.add(tok);
                        if (Character.isAlphabetic(line[i]) || isNumeric(String.valueOf(line[i])))
                        continue;
                    }                  
                }
                
            }  
            /*
            
            ***end of identiefiers checking*** 
            start of Literals checking
             */

            if (state == 0 && this.isNumeric(String.valueOf(line[i])) && (i + 1) < line.length && sss.length() == 0 || state == 0 && line[i] == '.' && sss.length() == 0 && !sss.toString().contains(".") && sss.length() != 0) {
                lit.append(line[i]);
                state = 1;

            } else if (state == 1 || state == 0 && this.isNumeric(String.valueOf(line[i]))) {
                if (this.isNumeric(String.valueOf(line[i]))) {
                    lit.append(line[i]);

                }
                if (line[i] == '.' && !sss.toString().contains(".")) {
                    lit.append(line[i]);

                }
                if ((i + 1) < line.length) {
                    if (this.isNumeric(String.valueOf(line[i + 1]))) {

                        continue;
                    }
                }

                int counter = 0;
                for (int z = 0; z < lit.toString().length(); z++) {
                    if (lit.toString().charAt(z) == '.') {
                        counter++;
                    }
                }
              
                if (counter > 1 && i + 1 == line.length || counter > 1 && this.isNumeric(String.valueOf(line[i])) != true) {

                    tok = new Tokenize();

                    tok.line_num = line_num;
                    tok.token = lit.toString();

                    tok.type = "Invalid Token";
                    line[i] = ' ';
                    lit = new StringBuilder();
                    all.add(tok);
                    state = 0;
                    counter = 0;
                } else if (this.isNumeric(String.valueOf(line[i])) != true && lit.toString().contains(".") || i + 1 == line.length && lit.toString().contains(".")) {
                    tok = new Tokenize();

                    tok.line_num = line_num;
                    tok.token = lit.toString();

                    tok.type = "LITERAL";
                    line[i] = ' ';
                    lit = new StringBuilder();
                    all.add(tok);
                    state = 0;
                } else if (this.isNumeric(String.valueOf(line[i])) != true && !lit.toString().contains(".") || i + 1 == line.length && !lit.toString().contains(".") || i + 1 == line.length && this.isNumeric(String.valueOf(line[i])) == true) {
                    tok = new Tokenize();

                    tok.line_num = line_num;
                    tok.token = lit.toString();

                    tok.type = "NUM";

                    lit = new StringBuilder();
                    all.add(tok);
                    state = 0;

                }
            }

            /*
                end of litral checking
            start calling functions to check the reserved words, Datatypes,symbols and operators
             */
            boolean evaluated=false;
            is_reserved(sss.toString(), line_num);
            is_datatype(sss.toString(), line_num);

           evaluated= is_Operator(line[i], line_num);
            evaluated=is_Symbol(line[i], line_num);
            /*
            end of checking reserved words, Datatypes,symbols and operators
             */
//            if (lit.length()==0&&sss.length()==0 && ! this.Check_alpha_or_digit(line[i]) && !evaluated &&  line[i]!=' '){
//             tok = new Tokenize();
//
//                    tok.line_num = line_num;
//                    tok.token =String.valueOf(line[i]) ;
//
//                    tok.type = "Invalid Token";
//                    line[i] = ' ';
//                    lit = new StringBuilder();
//                    all.add(tok);
//            
//            }
        }

    }

    public boolean is_reserved(String word, int line_num) {
        reserved_words all = new reserved_words();
        Tokenize tok = new Tokenize();
        for (int i = 0; i < all.words.length; i++) {

            if (all.words[i].equals(word.toLowerCase()) || all.words[i].equals(word.toUpperCase())) {
                tok.token = all.OUtput[i];

                tok.type = "RESERVED";
                tok.line_num = line_num;
                this.all.add(tok);
                sss = new StringBuilder();

                return true;
            }
        }

        return false;
    }

    public boolean Check_alpha_or_digit(char a) {

        if (Character.isAlphabetic(a) || Character.isDigit(a) || a == '_') {
            return true;
        }

        return false;

    }

    public boolean is_datatype(String word, int line_num) {
        reserved_words all = new reserved_words();
        Tokenize tok = new Tokenize();
        for (int i = 0; i < this.Data_types.length; i++) {

            if (Data_types[i].matches(word)) {
                tok.token = Data_types[i];

                tok.type = "DATATYPE";
                tok.line_num = line_num;
                this.all.add(tok);
                sss = new StringBuilder();

                return true;
            }
        }

        return false;

    }

    public boolean is_Symbol(char line, int line_num) {
        Tokenize tok = new Tokenize();
        Tokenize temp = new Tokenize();

        for (int k = 0; k < Symbols.length; k++) {
            if (line == Symbols[k]) {
                sss.append(Symbols[k]);
                tok = new Tokenize();
                tok.line_num = line_num;
                tok.token = String.valueOf(Symbols[k]);
                tok.type = "SYMMBOL";
                all.add(tok);
                sss = new StringBuilder();

                return true;

            }
        }

        return false;
    }

    public boolean is_Operator(char line, int line_num) {
        Tokenize tok = new Tokenize();
        Tokenize temp = new Tokenize();
        operators op = new operators();
        StringBuilder a = new StringBuilder();
        if (all.size() > 0) {
            temp = all.get(all.size() - 1);

            if (line == '=' && temp.token.equals("!") || line == '=' && temp.token.equals("<") || line == '=' && temp.token.equals(">") || line == '=' && temp.token.equals("=")) {
                a.append(temp.token);
                a.append(line);
                tok.token = a.toString();
                tok.type = "RELOP";
                tok.line_num = line_num;
                this.all.remove(all.size() - 1);
                this.all.add(tok);
                return true;
            }
        }
        for (int k = 0; k < op.ops.length; k++) {
            if (line == op.ops[k]) {
                sss.append(op.ops[k]);
                tok = new Tokenize();
                tok.line_num = line_num;
                tok.token = String.valueOf(op.ops[k]);
                tok.type = op.type[k];
                all.add(tok);
                sss = new StringBuilder();
                return true;

            }
        }
        return false;
    }

    public char[] remove_comments(char a[]) {
        StringBuilder temp1 = new StringBuilder();
        for (int p = 0; p < a.length; p++) {
            temp1.append(a[p]);
        }
        String temp = temp1.toString();
        //   System.out.println(temp);
        if (temp.contains("(*")) {
            int fir = temp.indexOf("(*");

            int sec = temp.indexOf('*');
            if (fir + 1 == sec) {
                temp.toCharArray()[sec] = ' ';
                int end = temp.toString().indexOf("*)");
                for (int i = fir; i < end + 1; i++) {
                    temp = temp.replace(temp.charAt(i), ' ');
                    temp = temp.replace(temp.charAt(i + 1), ' ');
                    //  System.out.println(temp.charAt(m)+" "+temp);
                }

            }
        }
        if (temp.contains("//")) {
            temp = temp.substring(0, temp.indexOf("//"));

        }
        temp = temp.trim();

        return temp.toCharArray();
    }

    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
