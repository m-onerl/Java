package jaxb;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "content", namespace = "http://xml.sportsdirectinc.com/sport/v2")
@XmlAccessorType(XmlAccessType.FIELD)
public class SportContent {
    @XmlElement(name = "team-sport-content")
    private TeamSportContent teamSportContent;

    @Override
    public String toString() {
        return teamSportContent != null ? teamSportContent.toString() : "";
    }
}

class TeamSportContent {
    @XmlElement(name = "sport")
    private Sport sport;

    @XmlElement(name = "league-content")
    private LeagueContent leagueContent;

    @Override
    public String toString() {
        String sportStr = sport != null ? sport.toString() : "";
        String leagueStr = leagueContent != null ? leagueContent.toString() : "";
        return sportStr + "\n" + leagueStr;
    }
}

class Sport {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @Override
    public String toString() {
        return "Sport: " + name + " (" + id + ")";
    }
}

class LeagueContent {
    @XmlElement(name = "league")
    private League league;

    @XmlElement(name = "season-content")
    private SeasonContent seasonContent;

    @Override
    public String toString() {
        String leagueStr = league != null ? league.toString() : "";
        String seasonStr = seasonContent != null ? seasonContent.toString() : "";
        return leagueStr + "\n" + seasonStr;
    }
}

class League {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private List<Name> names;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("League: ");
        if (names != null) {
            for (Name name : names) {
                sb.append(name.toString()).append(", ");
            }
        }
        return sb.toString();
    }
}

class SeasonContent {
    @XmlElement(name = "season")
    private Season season;

    @XmlElement(name = "conference-content")
    private ConferenceContent conferenceContent;

    @Override
    public String toString() {
        String seasonStr = season != null ? season.toString() : "";
        String conferenceStr = conferenceContent != null ? conferenceContent.toString() : "";
        return seasonStr + "\n" + conferenceStr;
    }
}

class Season {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "details")
    private Details details;

    @Override
    public String toString() {
        String detailsStr = details != null ? details.toString() : "";
        return "Season: " + name + " (" + id + "), " + detailsStr;
    }
}

class Details {
    @XmlElement(name = "start-date")
    private String startDate;

    @XmlElement(name = "end-date")
    private String endDate;

    @Override
    public String toString() {
        return "Start: " + startDate + ", End: " + endDate;
    }
}

class ConferenceContent {
    @XmlElement(name = "conference")
    private Conference conference;

    @XmlElement(name = "division-content")
    private DivisionContent divisionContent;

    @Override
    public String toString() {
        String conferenceStr = conference != null ? conference.toString() : "";
        String divisionStr = divisionContent != null ? divisionContent.toString() : "";
        return conferenceStr + "\n" + divisionStr;
    }
}

class Conference {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private List<Name> names;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Conference: ");
        if (names != null) {
            for (Name name : names) {
                sb.append(name.toString()).append(", ");
            }
        }
        return sb.toString();
    }
}

class DivisionContent {
    @XmlElement(name = "division")
    private Division division;

    @XmlElement(name = "team-content")
    private List<TeamContent> teamContents;

    @Override
    public String toString() {
        String divisionStr = division != null ? division.toString() : "";
        StringBuilder teamsStr = new StringBuilder();
        if (teamContents != null) {
            for (TeamContent teamContent : teamContents) {
                teamsStr.append(teamContent.toString()).append("\n");
            }
        }
        return divisionStr + "\n" + teamsStr;
    }
}

class Division {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "active")
    private boolean active;

    @Override
    public String toString() {
        return "Division: " + name + " (" + id + "), Active: " + active;
    }
}

class TeamContent {
    @XmlElement(name = "team")
    private Team team;

    @Override
    public String toString() {
        return team != null ? team.toString() : "";
    }
}

class Team {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private List<Name> names;

    @XmlElement(name = "location")
    private Location location;

    @XmlElement(name = "season-details")
    private SeasonDetails seasonDetails;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Team: ");
        if (names != null) {
            for (Name name : names) {
                sb.append(name.toString()).append(", ");
            }
        }
        String locationStr = location != null ? location.toString() : "";
        String seasonDetailsStr = seasonDetails != null ? seasonDetails.toString() : "";
        return sb.toString() + locationStr + ", " + seasonDetailsStr;
    }
}

class Location {
    @XmlElement(name = "city")
    private String city;

    @XmlElement(name = "state")
    private String state;

    @XmlElement(name = "country")
    private String country;

    @Override
    public String toString() {
        return "Location: " + city + ", " + state + ", " + country;
    }
}

class SeasonDetails {
    @XmlElement(name = "venue")
    private Venue venue;

    @Override
    public String toString() {
        return venue != null ? venue.toString() : "";
    }
}

class Venue {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private List<Name> names;

    @XmlElement(name = "location")
    private Location location;

    @XmlElement(name = "season-details")
    private VenueSeasonDetails venueSeasonDetails;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Venue: ");
        if (names != null) {
            for (Name name : names) {
                sb.append(name.toString()).append(", ");
            }
        }
        String locationStr = location != null ? location.toString() : "";
        String venueSeasonDetailsStr = venueSeasonDetails != null ? venueSeasonDetails.toString() : "";
        return sb.toString() + locationStr + ", " + venueSeasonDetailsStr;
    }
}

class VenueSeasonDetails {
    @XmlElement(name = "capacity")
    private int capacity;

    @XmlElement(name = "field-type")
    private FieldType fieldType;

    @Override
    public String toString() {
        String fieldTypeStr = fieldType != null ? fieldType.toString() : "";
        return "Capacity: " + capacity + ", " + fieldTypeStr;
    }
}

class FieldType {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @Override
    public String toString() {
        return "Field Type: " + name + " (" + id + ")";
    }
}
