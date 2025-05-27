package org.example.web.validation;

import javax.validation.GroupSequence;

@GroupSequence({ValidationGroups.NotBlankGroup.class, ValidationGroups.SizeGroup.class, ValidationGroups.PatternGroup.class})
public interface ValidationSequence {}

