/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@RequestMapping(headers = "HX-Request=true")
class OwnerHtmxController {

	private final OwnerController delegate;

	public OwnerHtmxController(OwnerController delegate) {
		this.delegate = delegate;
	}

	@ModelAttribute
	public void addStream(Model model, WebRequest request) {
		if (!"/owners/find".equals(request.getAttribute("org.springframework.web.util.UrlPathHelper.PATH", RequestAttributes.SCOPE_REQUEST))) {
			model.addAttribute("layout", true);
		}
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		delegate.setAllowedFields(dataBinder);
	}

	@GetMapping("/owners/new")
	public String initCreationForm(Map<String, Object> model) {
		return delegate.initCreationForm(model);
	}

	@PostMapping("/owners/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result, Map<String, Object> model) {
		return delegate.processCreationForm(owner, result);
	}

	@GetMapping("/owners/find")
	public String initFindFormFragments(Map<String, Object> model) {
		return delegate.initFindForm(model);
	}

	@GetMapping("/owners")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, Owner owner, BindingResult result,
			Model model) {
		return delegate.processFindForm(page, owner, result, model);
	}

	@GetMapping("/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		return delegate.initUpdateOwnerForm(ownerId, model);
	}

	@PostMapping("/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
			@PathVariable("ownerId") int ownerId) {
		return delegate.processUpdateOwnerForm(owner, result, ownerId);
	}

	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		return delegate.showOwner(ownerId);
	}

}
