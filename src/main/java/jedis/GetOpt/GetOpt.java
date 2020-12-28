package jedis.GetOpt;



import java.util.ArrayList;
import java.util.ListIterator;

public class GetOpt {

    public static final String ILLEGAL_CMDLINE_OPTION_ERR = "ILLEGAL_CMDLINE_OPTION_ERR";

    public GetOpt(String[] args, String optString) {
        theOptions = new ArrayList();
        int currOptIndex = 0;
        theCmdArgs = new ArrayList();
        theOptionMatcher = new OptionMatcher(optString);
        // fill in the options list
        for (int i = 0; i < args.length; i++) {
            String token = args[i];
            int tokenLength = token.length();
            if (token.equals("--")) {         // end of opts
                currOptIndex = i + 1;         // set index of first operand
                break;                      // end of options
            } else if (token.startsWith("-") && tokenLength == 2) {
                // simple option token such as '-s' found
                theOptions.add(new Option(token.charAt(1)));
            } else if (token.startsWith("-") && tokenLength > 2) {
                // stacked options found, such as '-shm'
                // iterate thru the tokens after the dash and
                // add them to theOptions list
                for (int j = 1; j < tokenLength; j++) {
                    theOptions.add(new Option(token.charAt(j)));
                }
            } else if (!token.startsWith("-")) {
                // case 1- there are not options stored yet therefore
                // this must be an command argument, not an option argument
                if (theOptions.size() == 0) {
                    currOptIndex = i;
                    break;              // stop processing options
                } else {
                    // case 2-
                    // there are options stored, check to see if
                    // this arg belong to the last arg stored
                    int indexoflast = 0;
                    indexoflast = theOptions.size() - 1;
                    Option op = (Option) theOptions.get(indexoflast);
                    char opLetter = op.getArgLetter();
                    if (!op.hasArg() && theOptionMatcher.hasArg(opLetter)) {
                        op.setArg(token);
                    } else {
                        // case 3 -
                        // the last option stored does not take
                        // an argument, so again, this argument
                        // must be a command argument, not
                        // an option argument
                        currOptIndex = i;
                        break;                  // end of options
                    }
                }
            }// end option does not start with "-"
        } // end for args loop

        //  attach an iterator to list of options
        theOptionsIterator = theOptions.listIterator();

        // options are done, now fill out cmd arg list with remaining args
        for (int i = currOptIndex; i < args.length; i++) {
            String token = args[i];
            theCmdArgs.add(token);
        }
    }


    /**
     * debugging routine to print out all options collected
     */
    public void printOptions() {
        for (ListIterator it = theOptions.listIterator(); it.hasNext(); ) {
            Option opt = (Option) it.next();
            System.out.print("OPT =" + opt.getArgLetter());
            String arg = opt.getArgument();
            if (arg != null) {
                System.out.print(" " + arg);
            }
            System.out.println();
        }
    }

    /**
     * gets the next option found in the commandline. Distinguishes
     * between two bad cases, one case is when an illegal option
     * is found, and then other case is when an option takes an
     * argument but no argument was found for that option.
     * If the option found was not declared in the optString, then
     * an IllegalArgumentException will be thrown (case 1).
     * If the next option found has been declared to take an argument,
     * and no such argument exists, then a MissingOptArgException
     * is thrown (case 2).
     *
     * @return int - the next option found.
     * @throws IllegalArgumentException, MissingOptArgException.
     */
    public int getNextOption() throws IllegalArgumentException {
        int retval = -1;
        if (theOptionsIterator.hasNext()) {
            theCurrentOption = (Option) theOptionsIterator.next();
            char c = theCurrentOption.getArgLetter();
            boolean shouldHaveArg = theOptionMatcher.hasArg(c);
            String arg = theCurrentOption.getArgument();
            if (!theOptionMatcher.match(c)) {
//                ErrorMsg msg = new ErrorMsg(ErrorMsg.ILLEGAL_CMDLINE_OPTION_ERR,
//                                            new Character(c));
                throw (new IllegalArgumentException(String.format("%s : %s", ILLEGAL_CMDLINE_OPTION_ERR, new Character(c))));
            } else if (shouldHaveArg && (arg == null)) {
                throw (new IllegalArgumentException(String.format("%s : %s", ILLEGAL_CMDLINE_OPTION_ERR, new Character(c))));
            }
            retval = c;
        }
        return retval;
    }

    /**
     * gets the argument for the current parsed option. For example,
     * in case of '-d <file>', if current option parsed is 'd' then
     * getOptionArg() would return '<file>'.
     *
     * @return String - argument for current parsed option.
     */
    public String getOptionArg() {
        String retval = null;
        String tmp = theCurrentOption.getArgument();
        char c = theCurrentOption.getArgLetter();
        if (theOptionMatcher.hasArg(c)) {
            retval = tmp;
        }
        return retval;
    }

    /**
     * gets list of the commandline arguments. For example, in command
     * such as 'cmd -s -d file file2 file3 file4'  with the usage
     * 'cmd [-s] [-d <file>] <file>...', getCmdArgs() would return
     * the list {file2, file3, file4}.
     *
     * @return String[] - list of command arguments that may appear
     * after options and option arguments.
     * @params none
     */
    public String[] getCmdArgs() {
        String[] retval = new String[theCmdArgs.size()];
        int i = 0;
        for (ListIterator it = theCmdArgs.listIterator(); it.hasNext(); ) {
            retval[i++] = (String) it.next();
        }
        return retval;
    }


    private Option theCurrentOption = null;
    private ListIterator theOptionsIterator;
    private ArrayList theOptions = null;
    private ArrayList theCmdArgs = null;
    private OptionMatcher theOptionMatcher = null;

    ///////////////////////////////////////////////////////////
    //
    //   Inner Classes
    //
    ///////////////////////////////////////////////////////////

    // inner class to model an option
    class Option {
        private char theArgLetter;
        private String theArgument = null;

        public Option(char argLetter) {
            theArgLetter = argLetter;
        }

        public void setArg(String arg) {
            theArgument = arg;
        }

        public boolean hasArg() {
            return (theArgument != null);
        }

        public char getArgLetter() {
            return theArgLetter;
        }

        public String getArgument() {
            return theArgument;
        }
    } // end class Option


    // inner class to query optString for a possible option match,
    // and whether or not a given legal option takes an argument.
    //
    class OptionMatcher {
        public OptionMatcher(String optString) {
            theOptString = optString;
        }

        public boolean match(char c) {
            boolean retval = false;
            if (theOptString.indexOf(c) != -1) {
                retval = true;
            }
            return retval;
        }

        public boolean hasArg(char c) {
            boolean retval = false;
            int index = theOptString.indexOf(c) + 1;
            if (index == theOptString.length()) {
                // reached end of theOptString
                retval = false;
            } else if (theOptString.charAt(index) == ':') {
                retval = true;
            }
            return retval;
        }

        private String theOptString = null;
    } // end class OptionMatcher
}// end class GetOpt
