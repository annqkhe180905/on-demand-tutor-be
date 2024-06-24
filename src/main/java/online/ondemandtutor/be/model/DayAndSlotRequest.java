package online.ondemandtutor.be.model;


import lombok.Data;

import java.util.List;

@Data

public class DayAndSlotRequest {
    Long weekDayIds;
    List<Long> teachingSlotIds;
}
