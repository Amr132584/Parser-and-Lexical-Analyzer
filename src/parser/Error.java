/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Amr
 */
public class Error {

    String found;
    String Excepected;
    int line_num;
String rule;
    public Error() {
    }

    public Error(int line_num, String Excepected, String found) {
        this.found = found;
        this.Excepected = Excepected;
        this.line_num = line_num;
    }

    public static void repor_error(Error er) {

        System.out.println("! Error in line : " + er.line_num + " excepected " + er.Excepected + " and found: " + er.found);

    }

}
