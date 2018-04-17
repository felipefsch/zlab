package converter;

import org.apache.commons.cli.*;

public class RateConverter {

	
	public static void main(String args[]) {
		Options options = new Options();

        Option input = new Option("c", "currency", true, "[USD,EUR,AUD,PLN]");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("d", "date", true, "YYYY-MM-DD");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Rate Converter", options);

            System.exit(1);
            return;
        }

        String currency = cmd.getOptionValue("currency");
        String date = cmd.getOptionValue("date");

        System.out.println(currency);
        System.out.println(date);

		
	}
}
