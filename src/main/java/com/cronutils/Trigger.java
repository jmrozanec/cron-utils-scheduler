package com.cronutils;

import com.google.common.base.Optional;
import org.threeten.bp.ZonedDateTime;

public interface Trigger {
    Optional<ZonedDateTime> nextExecution();
}
