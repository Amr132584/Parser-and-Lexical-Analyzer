/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import lexical.Tokenize;

/**
 *
 * @author Amr
 */
public class Tokens {

    public static ArrayList<Tokens> line = new ArrayList();
    public static Tokenize s = new Tokenize();
   public static int all_lines;
    public int line_num;
    public String token;
    public String Type;
    public static int curr_line = 1;
    public static int counter = -1;
    public static ArrayList<Tokens> Tokens = new ArrayList();

    public Tokens(int line_num, String token, String Type) {
        this.line_num = line_num;
        this.token = token;
        this.Type = Type;
    }

    public Tokens() {
    }

    public static void Insert_into_Array() {
        System.out.println("-----------------Lexical Part-------------------");

        s.tokenize();
        Tokens a = new Tokens();
        all_lines=s.all_lines;
        
      
        for (int i = 0; i < s.all.size(); i++) {
            a = new Tokens();
            a.line_num = s.all.get(i).line_num;

            a.Type = s.all.get(i).type;
            a.token = s.all.get(i).token;
            Tokens.add(a);
        }
        for (int g = 0; g < s.all.size(); g++) {
            System.out.println(Tokens.get(g).line_num + "\t\t" + Tokens.get(g).token + "\t\t" + Tokens.get(g).Type);
        }
        System.out.println("-----------------Parser Part-------------------");

    }

    public static void get_line() {
        line.clear();
        for (int i = 0; i < s.all.size(); i++) {
            if (Tokens.get(i).line_num == curr_line && i < Tokens.size()) {
                line.add(Tokens.get(i));

            }

        }
        curr_line++;
 counter = -1;
    }

    public static Tokens start(ArrayList<Tokens> x) {
        if (x.size() > 0) {
            Tokens tok = new Tokens();
            tok = (Tokens) x.get(0);
            counter = 0;
            return tok;
        } else {
            Tokens to = new Tokens();
            to.Type = "nothing";
            to.token = "nothing";
            if (x.size() > 0) {
                to.line_num = x.get(counter).line_num;
            } else {
                to.line_num = 1;
            }
            return to;
        }

    }

    public static boolean redo() {
        if (counter > -1) {
            counter--;
            return true;
        } else {
            return false;
        }
    }

    public static Tokens get_token(ArrayList<Tokens> x) {

        if (next(x)) {
            Tokens tok = new Tokens();
            tok = (Tokens) x.get(counter);
            return tok;
        } else {
            Tokens to = new Tokens();
            to.Type = "nothing";
            to.token = "nothing";
            if (x.size() > 0) {
                to.line_num = x.get(counter).line_num;
            } else {
                to.line_num = 1;
            }
            return to;
        }

    }

    public static boolean next(ArrayList x) {
        if (counter + 1 < x.size()) {
            counter++;
            return true;
        } else {
            return false;
        }
    }

    public static void reset_counter(int cou) {
        counter = cou;

    }
}
