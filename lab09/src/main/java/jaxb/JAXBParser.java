package jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBParser {
    public static String parse(File xmlFile) {
        try {
            JAXBContext context = JAXBContext.newInstance(SportContent.class, TeamSportContent.class, Sport.class, LeagueContent.class, League.class, SeasonContent.class, Season.class, Details.class, ConferenceContent.class, Conference.class, DivisionContent.class, Division.class, TeamContent.class, Team.class, Location.class, SeasonDetails.class, Venue.class, VenueSeasonDetails.class, FieldType.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            SportContent sportContent = (SportContent) unmarshaller.unmarshal(xmlFile);
            return sportContent.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
