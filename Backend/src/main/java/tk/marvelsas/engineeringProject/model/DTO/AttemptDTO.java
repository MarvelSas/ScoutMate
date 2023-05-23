package tk.marvelsas.engineeringProject.model.DTO;

import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_STATUS;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_TYPE;
import tk.marvelsas.engineeringProject.model.*;

import java.util.Collection;

public record AttemptDTO(
        Integer id,
        String name,
        Collection<AttemptTask> attemptTasks,
        AppUserDetailsDTO creatorId,
        AppUserDetailsDTO applicantId,
        Organization organization,
        ScoutRank scoutRankId,
        ScoutInstructorRank scoutInstructorRankId,
        MeritBadge meritBadgeId,
        ATTEMPT_STATUS STATUS,
        ATTEMPT_TYPE TYPE,
        boolean archived) {}
