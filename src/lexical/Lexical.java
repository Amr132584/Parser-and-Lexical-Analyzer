/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical;

/**
 *
// * @author Amr
 */
public class Lexical {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /*
        in order to run please add some mini Pascal code to the text file "code.txt" in the project folder
        */
   Tokenize s = new Tokenize();
        System.out.println("-----------------Lexical Part-------------------");
        System.out.println(s.is_reserved("begin", 0));
        s.tokenize();
        
    }
    
}
