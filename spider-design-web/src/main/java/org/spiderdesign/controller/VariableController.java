package org.spiderdesign.controller;

import org.spiderdesign.common.CURDController;
import org.spiderdesign.core.mapper.VariableMapper;
import org.spiderdesign.core.model.Variable;
import org.spiderdesign.core.service.VariableService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/variable")
public class VariableController extends CURDController<VariableService, VariableMapper, Variable> {
}
