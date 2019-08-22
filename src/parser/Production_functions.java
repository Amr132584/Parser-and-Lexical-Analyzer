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
public class Production_functions {

    Error er = new Error();
    Tokens tok = Tokens.get_token(Tokens.line);
    int num = 0;

    public class output {

        public int Progress = 0;
        public boolean Epsilon;
        public boolean complete;
        public Error Err;
        public String rule;
        public int nonterminal = 0;
 int end;
    }
    ArrayList<output> rules = new ArrayList();

    public Production_functions() {
        er.Excepected = "nothing";
        er.found = "nothing";
    }

    public boolean program(ArrayList<Tokens> line) {
        output set = new output();
        if (tok.token.equals("PROGRAM")) {
            set.Progress++;
            tok = Tokens.get_token(line);
            if (tok.Type.equals("IDENTIFIER")) {
                set.Progress++;
                tok = Tokens.get_token(line);
                if (tok.token.equals("(")) {
                    set.Progress++;
                    tok = Tokens.get_token(line);
                    if (this.Identifier_list(line)) {
                        set.Progress++;
                        set.nonterminal++;
                        if (tok.token.equals(")")) {
                            set.Progress++;
                            tok = Tokens.get_token(line);
                            if (tok.token.equals(";")) {
                                set.Progress++;
                                set.Epsilon = false;
                                set.complete = true;
                                set.rule = "program   program identifier ( identifier_list ) ; ";
                                rules.add(set);

                                return true;
                            } else {
                                er = new Error(tok.line_num, ";", tok.token);
                                set.Epsilon = false;
                                set.complete = false;
                                set.rule = "MULOP  -> *";
                                set.Err = er;
                                rules.add(set);

                            }
                        } else {
                            er = new Error(tok.line_num, ")", tok.token);
                            set.Epsilon = false;
                            set.complete = false;
                            set.rule = "MULOP  -> *";
                            set.Err = er;
                            rules.add(set);

                        }

                    }

                } else {
                    er = new Error(tok.line_num, "(", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "program";
                    set.Err = er;
                    rules.add(set);

                }

            } else {
                er = new Error(tok.line_num, "IDENTIFIER", tok.token);
                set.Epsilon = false;
                set.complete = false;
                set.rule = "program";
                set.Err = er;
                rules.add(set);

            }

        } ///////////////////////end of program  Declarations  ////////////////////////
        else if (this.Declarations(line)) {
            tok = Tokens.get_token(line);

            set.Progress++;
            set.nonterminal++;

            set.Epsilon = false;
            set.complete = true;
            set.rule = "program  Declarations ";
            rules.add(set);
            return true;
        } else if (this.Subprogram_declarations(line)) {
            set.Progress++;
            set.nonterminal++;
            set.Epsilon = false;
            set.complete = true;
            set.rule = "program  Subprogram_declarations";
            rules.add(set);
            return true;
        }
        if (this.compound_statement(line)) {
            set.Progress++;
            set.nonterminal++;
            set.Epsilon = false;
            set.complete = true;
            set.rule = "program  compound_statment";
            rules.add(set);
            return true;
        }
        Tokens.reset_counter(-1);
        tok = Tokens.get_token(line);

        return false;
    }

    public boolean Identifier_list(ArrayList<Tokens> line) {
        //   System.out.println(tok.token);
        output set = new output();
        if (tok.Type.equals("IDENTIFIER")) {
            set.Progress++;
            tok = Tokens.get_token(line);
            if (Identifier_list1(line)) {
                set.Progress++;
                set.nonterminal++;
                set.Epsilon = false;
                set.complete = true;
                set.rule = "Identifier_list -> IDENTIFIER  Identifier_list1";
                rules.add(set);

                return true;
            }
        }

        return false;

    }

    public boolean Identifier_list1(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.token.equals(",")) {
            set.Progress++;
            tok = Tokens.get_token(line);

            if (tok.Type.equals("IDENTIFIER")) {
                set.Progress++;
                tok = Tokens.get_token(line);
                if (Identifier_list1(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "Identifier_list1 -> , IDENTIFIER Identifier_list1";
                    rules.add(set);
                    return true;
                }
            } else {
                er = new Error(tok.line_num, "IDENTIFIER", tok.token);

                set.Epsilon = false;
                set.complete = false;
                set.rule = "Identifier_list1";
                set.Err = er;
                rules.add(set);
            }
        } else {
            set.Epsilon = true;
            set.complete = false;
            set.rule = "Identifier_list1 -> Epsilon";
            rules.add(set);

            return true;
        }
        return false;
    }

    public boolean Declarations(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.token.equals("VAR")) {
            set.Progress++;
            tok = Tokens.get_token(line);
            if (this.Identifier_list(line)) {
                set.Progress++;
                set.nonterminal++;
                if (tok.token.equals(":")) {
                    set.Progress++;
                    tok = Tokens.get_token(line);
                    if (type(line)) {
                        set.Progress++;
                        set.nonterminal++;
                        tok = Tokens.get_token(line);

                        if (tok.token.equals(";")) {
                            set.Progress++;
                            set.Epsilon = false;
                            set.complete = true;
                            set.rule = "declarations   var identifier_list : type ; declarations1";
                            rules.add(set);
                            return true;
                        } else {
                            er = new Error(tok.line_num, ";", tok.token);
                            set.Epsilon = false;
                            set.complete = false;
                            set.rule = "Declarations";
                            set.Err = er;
                            rules.add(set);
                        }
                    }else {
                    er = new Error(tok.line_num, "type", tok.token);

                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "Declarations";
                    set.Err = er;
                    rules.add(set);
                }
                } else {
                    er = new Error(tok.line_num, ":", tok.token);

                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "Declarations";
                    set.Err = er;
                    rules.add(set);
                }

            }else {
                    er = new Error(tok.line_num, "identifier", tok.token);

                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "Declarations";
                    set.Err = er;
                    rules.add(set);
                }
        }
        return false;

    }

    public boolean Declarations1(ArrayList<Tokens> line) {

        output set = new output();
        set.Epsilon = true;
        set.complete = false;
        set.rule = "Declarations1 -> Epsilon";
        rules.add(set);
        return true;

    }

    public boolean type(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.token.equals("ARRAY")) {
            set.Progress++;
            tok = Tokens.get_token(line);
            tok = Tokens.get_token(line);
            if (tok.token.equals("[")) {
                set.Progress++;
                tok = Tokens.get_token(line);
                if (tok.Type.equals("NUM")) {
                    set.Progress++;
                    tok = Tokens.get_token(line);
                    if (tok.token.equals(".")) {
                        set.Progress++;
                        tok = Tokens.get_token(line);
                        if (tok.token.equals(".")) {
                            set.Progress++;
                            tok = Tokens.get_token(line);
                            if (tok.Type.equals("NUM")) {
                                set.Progress++;
                                tok = Tokens.get_token(line);
                                if (tok.token.equals("]")) {
                                    set.Progress++;
                                    tok = Tokens.get_token(line);
                                    if (tok.token.equals("of")) {
                                        set.Progress++;
                                        tok = Tokens.get_token(line);
                                        if (this.standard_type(line)) {
                                            set.Progress++;
                                            set.nonterminal++;
                                            System.out.println(" ");
                                            set.Epsilon = false;
                                            set.complete = true;
                                            set.rule = "type  array [ num .. num ] of standard_type";
                                            rules.add(set);
                                            return true;
                                        }else {
                                        er = new Error(tok.line_num, "standard_type", tok.token);

                                        set.Epsilon = false;
                                        set.complete = false;
                                        set.rule = "type  array [ num .. num ] of standard_type";
                                        set.Err = er;
                                        rules.add(set);
                                    }
                                    } else {
                                        er = new Error(tok.line_num, "of", tok.token);

                                        set.Epsilon = false;
                                        set.complete = false;
                                        set.rule = "type  array [ num .. num ] of standard_type";
                                        set.Err = er;
                                        rules.add(set);
                                    }
                                } else {
                                    er = new Error(tok.line_num, "]", tok.token);

                                    set.Epsilon = false;
                                    set.complete = false;
                                    set.rule = "type  array [ num .. num ] of standard_type";
                                    set.Err = er;
                                    rules.add(set);
                                }

                            } else {
                                er = new Error(tok.line_num, "NUM", tok.token);

                                set.Epsilon = false;
                                set.complete = false;
                                set.rule = "type  array [ num .. num ] of standard_type";
                                set.Err = er;
                                rules.add(set);
                            }

                        } else {
                            er = new Error(tok.line_num, ".", tok.token);

                            set.Epsilon = false;
                            set.complete = false;
                            set.rule = "type  array [ num .. num ] of standard_type";
                            set.Err = er;
                            rules.add(set);
                        }

                    } else {
                        er = new Error(tok.line_num, ".", tok.token);

                        set.Epsilon = false;
                        set.complete = false;
                        set.rule = "type";
                        set.Err = er;
                        rules.add(set);
                    }
                } else {
                    er = new Error(tok.line_num, "NUM", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "type";
                    set.Err = er;
                    rules.add(set);
                }

            } else {
                er = new Error(tok.line_num, "[", tok.token);
                set.Epsilon = false;
                set.complete = false;
                set.rule = "type";
                set.Err = er;
                rules.add(set);
            }

        } else if (standard_type(line)) {
            set.Progress++;
            set.nonterminal++;
            set.Epsilon = false;
            set.complete = true;
            set.rule = "type -> standard_type";
            rules.add(set);
            return true;

        }
        return false;

    }

    public boolean standard_type(ArrayList<Tokens> line) {
        output set = new output();
        tok.token = tok.token.toLowerCase();
        if (tok.token.equals("float")) {

            set.Progress++;
            set.Epsilon = false;
            set.complete = true;
            set.rule = "standard_type -> float ";
            rules.add(set);
            return true;

        }
        if (tok.token.equals("integer")) {
            set.Progress++;
            set.Epsilon = false;
            set.complete = true;
            set.rule = "standard_type -> integer ";

            rules.add(set);

            return true;

        }
        return false;
    }
  public boolean Subprogram_head(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.token.equals("FUNCTION")) {
            tok = Tokens.get_token(line);
            set.Progress++;
            if (tok.Type.equals("IDENTIFIER")) {
                tok = Tokens.get_token(line);
                set.Progress++;
                
                if (this.arguments(line)) {
                    if (rules.get(rules.size() - 1).Epsilon == false && rules.get(rules.size() - 1).complete == true) {
                        tok = Tokens.get_token(line);
                    }
                    set.Progress++;
                    set.nonterminal++;
                    
                    if (tok.token.equals(":")) {
                        set.Progress++;
                        tok = Tokens.get_token(line);
                        if (this.standard_type(line)) {
                            set.Progress++;
                            set.nonterminal++;
                            tok = Tokens.get_token(line);

                            if (tok.token.equals(";")) {
                                set.Progress++;
                                set.Epsilon = false;
                                set.complete = true;
                                
                                set.rule = "subprogram_head  function identifier arguments : standard_type ; ";
                                rules.add(set);
                                
                                return true;
                            }else {
            er = new Error(tok.line_num, ";", tok.token);

                set.Epsilon = false;
                set.complete = false;
                set.rule = "Subprogram_head";
                set.Err = er;
                rules.add(set);
            
            
            
            }
                        }else {
            er = new Error(tok.line_num, "Standard_type", tok.token);

                set.Epsilon = false;
                set.complete = false;
                set.rule = "Subprogram_head";
                set.Err = er;
                rules.add(set);
            
            
            
            }
                    }else {
            er = new Error(tok.line_num, ":", tok.token);

                set.Epsilon = false;
                set.complete = false;
                set.rule = "Subprogram_head";
                set.Err = er;
                rules.add(set);
            
            
            
            }
                }else {
            er = new Error(tok.line_num, "arguments", tok.token);

                set.Epsilon = false;
                set.complete = false;
                set.rule = "Subprogram_head";
                set.Err = er;
                rules.add(set);
            
            
            
            }
            }else {
            er = new Error(tok.line_num, "IDENTIFIER", tok.token);

                set.Epsilon = false;
                set.complete = false;
                set.rule = "Subprogram_head";
                set.Err = er;
                rules.add(set);
            
            
            
            }

        }  if (tok.token.equals("PROCEDURE")) {
            set.Progress++;

            tok = Tokens.get_token(line);

            if (tok.Type.equals("IDENTIFIER")) {
                tok = Tokens.get_token(line);
                set.Progress++;

                if (this.arguments(line)) {
                    if (rules.get(rules.size() - 1).Epsilon == false && rules.get(rules.size() - 1).complete == true) {
                        tok = Tokens.get_token(line);
                    }
                    set.Progress++;
                    set.nonterminal++;
                    if (tok.token.equals(";")) {
                        set.Progress++;
                        set.Epsilon = false;
                        set.complete = true; 
                        set.rule = "subprogram_head  PROCEDURE identifier arguments ; ";
                        rules.add(set);

                        return true;

                    } er = new Error(tok.line_num, ";", tok.token);

                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "subprogram_head";
                    set.Err = er;
                    rules.add(set);

                }else {
                    er = new Error(tok.line_num, "arguments", tok.token);

                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "subprogram_head";
                    set.Err = er;
                    rules.add(set);
                }

            } else {
                er = new Error(tok.line_num, "IDENTIFIER", tok.token);

                set.Epsilon = false;
                set.complete = false;
                set.rule = "Subprogram_head";
                set.Err = er;
                rules.add(set);

            }
        }

        return false;

    }

    public boolean Subprogram_declarations(ArrayList<Tokens> line) {//not tested
        output set = new output();

        if (this.Subprogram_declaration(line)) {
            set.Progress++;
            set.nonterminal++;
            tok = Tokens.get_token(line);
           
            if (tok.token.equals(";")) {
                set.Progress++;
              //  tok = Tokens.get_token(line);
                if (this.Subprogram_declarations1(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "subprogram_declarations   subprogram_declaration ; subprogram_declarations1";
                    rules.add(set);
                    return true;
                }

            } er = new Error(tok.line_num, ";", tok.token);

                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "subprogram_head";
                    set.Err = er;
                    rules.add(set);

        }
        return false;

    }

    public boolean Subprogram_declarations1(ArrayList<Tokens> line) {//not tested
        output set = new output();

        set.Epsilon = true;
        set.complete = false;
        set.rule = "Subprogram_declarations1 -> Epsilon ";
        rules.add(set);
        return true;

    }

    public boolean Subprogram_declaration(ArrayList<Tokens> line) {
        output set = new output();
        if (this.Subprogram_head(line)) {
            tok = Tokens.get_token(line);
            set.Progress++;
            set.nonterminal++;
            if (this.Declarations(line)) {
                set.Progress++;
                set.nonterminal++;
                tok = Tokens.get_token(line);
          
                if (this.compound_statement(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "subprogram_declaration  subprogram_head  declarations compound_statement ";
                    rules.add(set);
                    return true;
                } er = new Error(tok.line_num, "begin", tok.token);

                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "Subprogram_declaration";
                    set.Err = er;
                    rules.add(set);

            }else {
             er = new Error(tok.line_num, "Declarations", tok.token);
                set.Epsilon = false;
                set.complete = false;
                set.rule = "Subprogram_declaration";
                set.Err = er;
                rules.add(set);
            
            }
            

        }
        return false;

    }

  
    public boolean arguments(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.token.equals("(")) {
            tok = Tokens.get_token(line);
            set.Progress++;
            if (this.Parameter_list(line)) {
                set.Progress++;
                set.nonterminal++;
                //  tok = Tokens.get_token(line);
                if (tok.token.equals(")")) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "arguments -> (Parameter_list) ";
                    rules.add(set);
                    return true;
                } else {
                    er = new Error(tok.line_num, ")", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = ":";
                    set.Err = er;
                    rules.add(set);
                }

            }

        } else {
            set.Epsilon = true;
            set.complete = false;
            set.rule = "arguments -> Epsilon";
            rules.add(set);
            return true;
        }
        return false;

    }

    public boolean Parameter_list(ArrayList<Tokens> line) {
        output set = new output();

        if (this.Identifier_list(line)) {
            set.Progress++;
            set.nonterminal++;
            //    tok = Tokens.get_token(line);
            if (tok.token.equals(":")) {
                set.Progress++;

                tok = Tokens.get_token(line);
                if (this.type(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    tok = Tokens.get_token(line);

                    if (this.Parameter_list1(line)) {
                        set.Progress++;
                        set.nonterminal++;
                        set.Epsilon = false;
                        set.complete = true;
                        set.rule = "Parameter_list ->   identifier_list : type parameter_list1 ) ; ";
                        rules.add(set);
                        return true;
                    }
                }

            } else {
                er = new Error(tok.line_num, ":", tok.token);
                set.Epsilon = false;
                set.complete = false;
                set.rule = "Parameter_list";
                set.Err = er;
                rules.add(set);
            }

        }

        return false;

    }

    public boolean Parameter_list1(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.token.equals(";")) {
            set.Progress++;
            tok = Tokens.get_token(line);
            if (this.Identifier_list(line)) {
                set.Progress++;
                set.nonterminal++;
                //    tok = Tokens.get_token(line);
                if (tok.token.equals(":")) {
                    set.Progress++;
                    tok = Tokens.get_token(line);
                    if (this.type(line)) {
                        set.Progress++;
                        set.nonterminal++;
                        tok = Tokens.get_token(line);

                        if (this.Parameter_list1(line)) {
                            set.Progress++;
                            set.nonterminal++;
                            set.Epsilon = false;
                            set.complete = true;
                            set.rule = "Parameter_list1 -> ; identifier_list : type parameter_list1";
                            rules.add(set);

                            return true;
                        }
                    }

                } else {
                    er = new Error(tok.line_num, ":", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "Parameter_list1";
                    set.Err = er;
                    rules.add(set);
                }

            }

        } else {
            set.Epsilon = true;
            set.complete = false;
            set.rule = "Parameter_list1 -> epsilon";
            rules.add(set);
            return true;
        }
        return false;

    }
    public boolean compound_statement(ArrayList<Tokens> line) {
        output set = new output();
        if (tok.token.equals("BEGIN")) {
            set.Progress++;
            tok = Tokens.get_token(line);

            if (this.Optional_statments(line)) {
                set.Progress++;
                set.nonterminal++;

                if (tok.token.equals("END")) {
                    set.Progress++;

                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "compound_statement -> begin Optional_statments end";
                    rules.add(set);
                    return true;

                } else {

                    er = new Error(tok.line_num, "end", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "compound_statement";
                    set.Err = er;
                    rules.add(set);

                }
            }
        }
        return false;
    }

    public boolean Optional_statments(ArrayList<Tokens> line) {
        output set = new output();

        if (this.statement_list(line)) {

            set.Progress++;
            set.nonterminal++;
            set.Epsilon = false;
            set.complete = true;
            set.rule = "Optional_statments -> statement_list ";
            rules.add(set);
            return true;

        } else {
            set.Epsilon = true;
            set.complete = false;
            set.rule = "Optional_statments -> Epsilon ";
            rules.add(set);
            return true;
        }

    }

    public boolean statement_list1(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.token.equals(";")) {
            set.Progress++;
            tok = Tokens.get_token(line);
            if (this.expression(line)) {
                set.Progress++;
                set.nonterminal++;
                if (this.statement_list1(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "statement_list1   ; statement statement_list1 ";
                    rules.add(set);
                    return true;
                }

            }

        } else {
            set.Epsilon = true;
            set.complete = false;
            set.rule = "statement_list1 -> epsilon ";
            rules.add(set);
            return true;
        }
        return false;

    }

    public boolean statement_list(ArrayList<Tokens> line) {
        output set = new output();

        if (this.statement(line)) {
            set.Progress++;
            set.nonterminal++;

            if (this.statement_list1(line)) {
                set.Progress++;
                set.nonterminal++;
                set.Epsilon = false;
                set.complete = true;
                set.rule = "statement_list -> statement statement_list1";
                rules.add(set);

                return true;

            }

        }
        return false;
    }

    /*
 statement   variable assignop expression 
 | procedure_statement 
 | compound_statement 
 | if expression then statement else statement 
 | while expression do statement 
  
     */
    public boolean statement(ArrayList<Tokens> line) {
        output set = new output();
//statement   variable assignop expression 

        if (this.variable(line)) {
           // tok = Tokens.get_token(line);
            set.Progress++;
            set.nonterminal++;
            if (tok.token.equals("]"))tok = Tokens.get_token(line);
            if (this.assignop(line)) {
                set.Progress++;
                set.nonterminal++;

                tok = Tokens.get_token(line);
                
                if (this.expression(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "statement    variable assignop expression ";
                    rules.add(set);
                    return true;
                }
            } else {
                Tokens.redo();
                Tokens.redo();
                tok = Tokens.get_token(line);
                
            }

        } else {
            Tokens.redo();
        }
//statement  Procedure_statement
        if (this.Procedure_statement(line)) {
            set.Progress++;
            set.nonterminal++;
            set.Epsilon = false;
            set.complete = true;
            set.rule = "statement    Procedure_statment ";
            rules.add(set);
            return true;

        }
        //statement   if expression then statement else statement
     
        if (tok.token.equals("if")) {
            tok = Tokens.get_token(line);
            tok = Tokens.get_token(line);
            set.Progress++;

            if (this.expression(line)) {
                if (tok.token.equals("THEN")) {
                    tok = Tokens.get_token(line);
                    set.Progress++;

                    if (this.statement(line)) { 
                        set.Progress++;
                        set.nonterminal++;  
                        if (tok.token.equals("ELSE")) {
                            set.Progress++;

                            tok = Tokens.get_token(line);
                            if (this.statement(line)) {
                                set.Progress++;
                                set.nonterminal++;
                                set.Epsilon = false;
                                set.complete = true;
                                set.rule = "statement    if expression then statement else statement ";
                                rules.add(set);
                                return true;
                            }
                        } else {
                            er = new Error(tok.line_num, "else", tok.token);
                            set.Epsilon = false;
                            set.complete = false;
                            set.rule = "statement";
                            set.Err = er;
                            rules.add(set);
                        }

                    }
                } else {

                    er = new Error(tok.line_num, "then", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "statement";
                    set.Err = er;
                    rules.add(set);

                }
            }
//statement  while expression do statement
        } else if (tok.token.equals("WHILE")) {
            set.Progress++;

            tok = Tokens.get_token(line);
            tok = Tokens.get_token(line);
            if (this.expression(line)) {
                set.Progress++;
                set.nonterminal++;
                if (tok.token.equals("DO")) {
                    set.Progress++;

                    tok = Tokens.get_token(line);
                    if (this.statement(line)) {
                        set.Progress++;
                        set.nonterminal++;
                        set.Epsilon = false;
                        set.complete = true;
                        set.rule = "statement   while expression do statement ";
                        rules.add(set);

                        return true;

                    }

                } else {

                    er = new Error(tok.line_num, "do", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "statement";
                    set.Err = er;
                    rules.add(set);
                }

            }else {

                    er = new Error(tok.line_num, "exression", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "statement";
                    set.Err = er;
                    rules.add(set);
                }


        }
        //statement  compound_statement
        if (this.compound_statement(line)) {
            set.Progress++;
            set.nonterminal++;

            set.Epsilon = false;
            set.complete = true;
            set.rule = "statement  compound_statment";
            rules.add(set);
            return true;

        }
        return false;

    }

    public boolean variable(ArrayList<Tokens> line) {
        output set = new output();
        if (tok.Type.equals("IDENTIFIER")) {
            set.Progress++;

            tok = Tokens.get_token(line);
            num++;

            if (tok.token.equals("[")) {
                set.Progress++;
                tok = Tokens.get_token(line);

                if (expression(line)) {
                    set.Progress++;
                    set.nonterminal++;

                    //   tok = Tokens.get_token(line);
                    if (tok.token.equals("]")) {
                        set.Progress++;
                        set.Epsilon = false;
                        set.complete = true;
                        set.rule = "Variable   id [ expression ] ";
                        rules.add(set);
                        return true;

                    } else {
                        er = new Error(tok.line_num, "]", tok.token);
                        set.Epsilon = false;
                        set.complete = false;
                        set.rule = "]";
                        set.Err = er;
                        rules.add(set);
                    }
                }
            } else {
                set.Epsilon = false;
                set.complete = true;
                set.rule = "Variable   id ";
                rules.add(set);
                return true;
            }
        }
        return false;

    }

    public boolean Procedure_statement(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.Type.equals("IDENTIFIER")) {
            tok = Tokens.get_token(line);
            set.Progress++;
            if (tok.token.equals("(")) {
                tok = Tokens.get_token(line);
                set.Progress++;

                if (expression_list(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    //tok = Tokens.get_token(line);

                    if (tok.token.equals(")")) {
                        set.Progress++;
                        set.Epsilon = false;
                        set.complete = true;
                        set.rule = "Procedure_statment   identifier ( expression_list )";
                        rules.add(set);

                        return true;

                    } else {
                        er = new Error(tok.line_num, ")", tok.token);
                        set.Epsilon = false;
                        set.complete = false;
                        set.rule = "]";
                        set.Err = er;
                        rules.add(set);

                    }
                }
            } else {

                set.Epsilon = false;
                set.complete = true;
                set.rule = "Procedure_statment   identifier ";
                rules.add(set);
                Tokens.redo();
                return true;
            }
        }
        return false;

    }

    public boolean expression_list(ArrayList<Tokens> line) {
        output set = new output();
        if (expression(line)) {
            set.Progress++;
            set.nonterminal++;
            if (expression_list1(line)) {
                set.Progress++;
                set.nonterminal++;
                System.out.println("expression_list   expression expression_list1 ");
                set.Epsilon = false;
                set.complete = true;
                set.rule = "expression_list   expression expression_list1 ";
                rules.add(set);
                return true;

            }

        }
        return false;

    }

    public boolean expression_list1(ArrayList<Tokens> line) {
        output set = new output();
        if (tok.token.equals(",")) {
            set.Progress++;
            tok = Tokens.get_token(line);

            if (expression(line)) {
                set.Progress++;
                set.nonterminal++;
                //tok = Tokens.get_token(line);
                if (expression_list1(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "expression_list1 -> , expression expression_list1";
                    rules.add(set);

                    return true;
                }

            }
        } else {
            set.Epsilon = true;
            set.complete = false;
            set.rule = "expression_list1 -> Epsilon";
            rules.add(set);
            return true;

        }
        return false;

    }

    public boolean expression(ArrayList<Tokens> line) {
        output set = new output();

        if (simple_expression(line)) {
            // tok = Tokens.get_token(line);

            set.Progress++;
            set.nonterminal++;
            if (relop(line)) {
                set.Progress++;
                set.nonterminal++;
                tok = Tokens.get_token(line);

                if (simple_expression(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "expression  simple_expression relop simple_expression ";
                    rules.add(set);
                    return true;
                }
            } else {

                set.Epsilon = false;
                set.complete = true;
                set.rule = "expression  simple_expression";
                rules.add(set);
                return true;
            }
        }
        return false;

    }

    public boolean simple_expression(ArrayList<Tokens> line) {
        output set = new output();

        if (term(line)) {
            set.Progress++;
            set.nonterminal++;
            // tok = Tokens.get_token(line);

            if (simple_expression1(line)) {

                set.Progress++;
                set.nonterminal++;
                set.Epsilon = false;
                set.complete = true;
                set.rule = "simple_expression  term simple_expression1 ";
                rules.add(set);

                return true;

            }

        }
        return false;

    }

    public boolean simple_expression1(ArrayList<Tokens> line) {
        output set = new output();

        if (addop(line)) {
            set.Progress++;
            set.nonterminal++;
            tok = Tokens.get_token(line);
       
            if (term(line)) {
                set.Progress++;
                set.nonterminal++;
                //       tok = Tokens.get_token(line);
                if (simple_expression1(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "simple_expression1 -> addop term simple_expression1 ";
                    rules.add(set);

                    return true;
                }

            } else {
                er = new Error(tok.line_num, "term", tok.token);
                set.Epsilon = false;
                set.complete = true;
                set.rule = "simple_expression1 -> addop term simple_expression1 ";
                set.Err = er;
                rules.add(set);

            }

        } else {

            set.Epsilon = true;
            set.complete = false;
            set.rule = "simple_expression1 -> Epsilon";
            rules.add(set);
            return true;
        }
        return false;

    }

    public boolean term(ArrayList<Tokens> line) {
        output set = new output();

        if (factor(line)) {
            set.Progress++;
            set.nonterminal++;
            tok = Tokens.get_token(line);
            if (term1(line)) {
                set.Progress++;
                set.nonterminal++;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "term  factor term1 ";
                rules.add(set);

                return true;

            }
        }
        return false;

    }

    public boolean term1(ArrayList<Tokens> line) {
        output set = new output();

        if (mulop(line)) {
            set.Progress++;
            set.nonterminal++;
            tok = Tokens.get_token(line);

            if (factor(line)) {
                set.Progress++;
                set.nonterminal++;
                tok = Tokens.get_token(line);
                if (term1(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "term1 -> mulop factor term1";
                    rules.add(set);

                    return true;
                }

            } else {
                er = new Error(tok.line_num, "factor", tok.token);

                set.Epsilon = false;
                set.complete = false;
                set.rule = "term1 -> mulop factor term1";
                set.Err = er;
                rules.add(set);
            }

        } else {
            set.Epsilon = true;
            set.complete = false;
            set.rule = "term1 -> Epsilon";
            rules.add(set);
            return true;
        }
        return false;

    }

    public boolean factor(ArrayList<Tokens> line) {
        output set = new output();
        Error er = new Error();

        //factor   id ( expression_list )
        if (tok.Type.equals("IDENTIFIER")) {
            set.Progress++;

            tok = Tokens.get_token(line);
            num++;

            if (tok.token.equals("(")) {
                set.Progress++;

                tok = Tokens.get_token(line);
                if (expression_list(line)) {
                    set.Progress++;
                    set.nonterminal++;
                    tok = Tokens.get_token(line);

                    if (tok.token.equals(")")) {
                        set.Progress++;
                        set.Epsilon = false;
                        set.complete = true;
                        set.rule = "factor   identifier ( expression_list)";
                        rules.add(set);

                        return true;

                    } else {
                        er = new Error(tok.line_num, ")", tok.token);
                        set.Epsilon = false;
                        set.complete = true;
                        set.rule = "factor   identifier ( expression_list) ";
                        rules.add(set);
                    }
                }
            } else {

                set.Epsilon = false;
                set.complete = true;
                set.rule = "factor   identifier ";
                rules.add(set);
                Tokens.redo();
                return true;
            }

        }
//factor  not factor
        set = new output();
        if (tok.token.equals("NOT")) {
            set.Progress++;
            tok = Tokens.get_token(line);

            if (factor(line)) {
                set.Progress++;
                set.nonterminal++;
                set.Epsilon = false;
                set.complete = true;
                set.rule = "factor   not factor";
                rules.add(set);
                return true;
            }

        }

        // factor   ( expression )
        set = new output();

        if (tok.token.equals("(")) {
            tok = Tokens.get_token(line);
            set.Progress++;
            if (expression(line)) {
                set.Progress++;
                set.nonterminal++;
                // tok = Tokens.get_token(line);

                if (tok.token.equals(")")) {
                    set.Progress++;

                    set.Epsilon = false;
                    set.complete = true;
                    set.rule = "factor   ( expression )  ";
                    rules.add(set);
                    return true;

                } else {
                    er = new Error(tok.line_num, ")", tok.token);
                    set.Epsilon = false;
                    set.complete = false;
                    set.rule = "factor    ( expression ) ";
                    set.Err = er;
                    rules.add(set);
                }
            }
        }
        //factor  num
        set = new output();

        if (tok.Type.equals("NUM")) {

            set.Progress++;

            set.Epsilon = false;
            set.complete = true;
            set.rule = "factor   NUM  ";

            rules.add(set);
            return true;
        } else {
            er = new Error(tok.line_num, "NUM", tok.token);
            set.Epsilon = false;
            set.complete = false;
            set.rule = "factor   identifier  ";
            set.Err = er;
            rules.add(set);
            return false;
        }

    }

    public boolean assignop(ArrayList<Tokens> line) {
        output set = new output();
        if (tok.Type.equals("ASSIGNOP")) {
            set.Progress = 1;

            set.Epsilon = false;
            set.complete = true;
            set.rule = "ASSIGNOP   =";
            rules.add(set);
            return true;

        } else {
            er = new Error(tok.line_num, "ASSIGNOP", tok.token);
            set = new output();
            set.Progress = 1;

            set.Epsilon = false;
            set.complete = false;
            set.rule = "ASSIGNOP  -> =";
            set.Err = er;
            rules.add(set);
        }
        return false;
    }

    public boolean addop(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.Type.equals("ADDOP")) {
            if (tok.token.equals("+")) {
                set = new output();

                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "ADDOP  +";
                rules.add(set);
                return true;
            }
            if (tok.token.equals("-")) {
                set = new output();

                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "ADDOP  -";
                rules.add(set);
                return true;
            }

        }
//        else {
//            er = new Error(tok.line_num, "ADDOP", tok.token);
//
//            set = new output();
//            set.Progress = 1;
//
//            set.Epsilon = false;
//            set.complete = false;
//            set.rule = "ADDOP  -> *";
//            set.Err = er;
//            rules.add(set);
//        }
        return false;
    }

    public boolean mulop(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.Type.equals("MULOP")) {

            if (tok.token.equals("*")) {
                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "MULOP  -> * ";
                rules.add(set);
                return true;
            } else if (tok.token.equals("/")) {
                set = new output();
                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "MULOP  -> / ";
                rules.add(set);
                return true;
            }
        }
//        else {
//            er = new Error(tok.line_num, "MULOP", tok.token);
//            set = new output();
//            set.Progress = 1;
//
//            set.Epsilon = false;
//            set.complete = false;
//            set.rule = "MULOP  -> *";
//            set.Err = er;
//            rules.add(set);
//        }

        return false;

    }

    public boolean relop(ArrayList<Tokens> line) {
        output set = new output();

        if (tok.Type.equals("RELOP")) {
            //tok = Tokens.get_token(line);
            if (tok.token.equals("<")) {
                set = new output();
                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "RELOP  -> > ";
                rules.add(set);
                return true;
            }
            if (tok.token.equals(">")) {
                set = new output();
                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "RELOP  -> >";
                rules.add(set);
                return true;
            }
            if (tok.token.equals("==")) {
                set = new output();
                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "RELOP  -> == ";
                rules.add(set);
                return true;
            }
            if (tok.token.equals("<=")) {
                set = new output();
                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "RELOP  -> <=";
                rules.add(set);
                return true;
            }
            if (tok.token.equals(">=")) {
                set = new output();
                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "RELOP  -> >= ";
                rules.add(set);
                return true;
            }
            if (tok.token.equals("!=")) {
                set = new output();
                set.Progress = 1;

                set.Epsilon = false;
                set.complete = true;
                set.rule = "RELOP  -> != ";
                rules.add(set);
                return true;
            }
        }
////        else {
////            er = new Error(tok.line_num, "RELOP", tok.token);
////            set = new output();
////            set.Progress = 1;
////            set.Epsilon = false;
////            set.complete = false;
////            set.rule = "RELOP  -> != ";
////            set.Err = er;
////            rules.add(set);
////        }
        return false;

    }
}
