/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;

/**
 *
 * @author Amr
 */
public class Parser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tokens tok = new Tokens();
        Tokens.Insert_into_Array();
        boolean d = false;
        boolean extra = false;
        boolean error_flag;
        boolean matched;
        for (int z = 0; z < Tokens.all_lines; z++) {
            extra = false;
            d = false;
            Tokens.get_line();
            if (Tokens.line.size() == 0) {
                continue;
            }
            System.out.println("--------------------- line number " + (z + 1) + " -------------------");
            System.out.println("Production Rule                                              Number of non Terminals in Production Rule ");

            error_flag = false;
            matched = false;

            Production_functions a = new Production_functions();
//            for (int v=0;v<Tokens.line.size();v++)
//            System.out.println(Tokens.line.get(v).token);
//if (!d){
//if (a.Subprogram_head(Tokens.line) && ! matched){
//    System.out.println("hhhh");
// d = true;
//}else Tokens.reset_counter(-1);}
            if (a.program(Tokens.line)) {

                d = true;
            } else {
                Tokens.reset_counter(-1);
            }

            if (!d) {
                if (a.Identifier_list(Tokens.line)) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.Declarations(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.type(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            } if (!d) {
                if (a.Subprogram_head(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }

            if (!d) {
                if (a.Subprogram_declarations(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.Subprogram_declaration(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
           
            if (!d) {
                if (a.Parameter_list(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.compound_statement(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.Optional_statments(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.statement_list(Tokens.line) && !matched) {
                    d = true;;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.statement(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.variable(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.Procedure_statement(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.expression_list(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.expression(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.simple_expression(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.term(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
            }
            if (!d) {
                if (a.factor(Tokens.line) && !matched) {
                    d = true;
                } else {
                    Tokens.reset_counter(-1);
                }
                if (!d) {
                    if (a.arguments(Tokens.line) && !matched) {
                        d = true;
                    } else {
                        Tokens.reset_counter(-1);
                    }
                }
            }
            a.tok = Tokens.get_token(Tokens.line);

            if (a.tok.token.equals("END")) {
                a.tok = Tokens.get_token(Tokens.line);
            }
            if (!a.tok.token.equals("nothing")) {
                extra = true;
            }
            ////////////////////end of production functions calling///////////////
            for (int i = a.rules.size() - 1; i >= 0; i--) {
                if (a.rules.get(i).complete) {
                    matched = true;
                    System.out.print(a.rules.get(i).rule);
                    System.out.print("\t\t\t\t\t " + a.rules.get(i).nonterminal);
                    System.out.print("\n\t");

                }
              
                if (a.rules.get(i).Progress >= 1 && a.rules.get(i).Err != null) {

                    a.er.Excepected = a.rules.get(i).Err.Excepected;
                    a.er.found = a.rules.get(i).Err.found;
                    a.er.line_num = a.rules.get(i).Err.line_num;
                    a.er.rule = a.rules.get(i).rule;

                    error_flag = true;
                } else if (!a.rules.get(i).Epsilon == false && !a.rules.get(i).complete == false) {
                    a.er.Excepected = a.rules.get(i).Err.Excepected;

                }
            }

            if (matched == true && !extra && error_flag == false && !extra) {
                System.out.println("");
                System.out.println("No errors found ");
            } else if (a.er != null && !a.er.Excepected.equals("nothing")) {
                System.out.println("");
                System.out.println("\nError in line " + (Tokens.curr_line - 1) + " Exceepected : '" + a.er.Excepected + "' Found : '" + a.er.found + "' from rule : '" + a.er.rule + "'");

            } else if (d == true && !a.tok.token.equals("nothing") && extra) {

                System.out.println("\nError1 in line " + (z + 1) + " Exceepected : 'nothing' Found : '" + a.tok.token + "'");
            }

            System.out.println("\n\n\n");
        }
//        Tokens.Insert_into_Array();
//        Tokens.get_line();
//        
//        Tokens tok = Tokens.get_token(Tokens.line);
//        for (int i =0;i<Tokens.line.size();i++){
//            System.out.println(Tokens.line.get(i).token);
//        }
//        System.out.println(tok.token);

    }

}
