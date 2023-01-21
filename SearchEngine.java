import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.

    // inputStrings is the list of strings that are recorded from the user.
    ArrayList<String> inputStrings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (!url.getPath().equals("/")) {
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String stringToReturn = "Your search results: ";
                    for (int i = 0; i < inputStrings.size(); i++) {
                        if (inputStrings.get(i).contains(parameters[1])) {
                            stringToReturn += inputStrings.get(i) + " ";
                        }
                    }
                    return stringToReturn;
                }
            } else if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    inputStrings.add(parameters[1]);
                    return String.format("Added string: %s", parameters[1]);
                }
            } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String returnString = "Your search results: ";
                    for (int i = 0; i < inputStrings.size(); i++) {
                        if (inputStrings.get(i).contains(parameters[1])) {
                            returnString += inputStrings.get(i) + " ";
                        }
                    }
                    return returnString;
                }
            }
            return "404 Not Found!";
        } else {
            return "Your strings: " + inputStrings.toString();
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
