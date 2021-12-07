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

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping(path = "/owners/{ownerId}", headers = "HX-Request=true")
class PetHtmxController {

	private final PetController delegate;

	public PetHtmxController(PetController delegate) {
		this.delegate = delegate;
	}

	@ModelAttribute
	public void addStream(Model model) {
		model.addAttribute("layout", true);
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return delegate.populatePetTypes();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return delegate.findOwner(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		delegate.initOwnerBinder(dataBinder);
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		delegate.initPetBinder(dataBinder);
	}

	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		return delegate.initCreationForm(owner, model);
	}

	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) {
		return delegate.processCreationForm(owner, pet, result, model);
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable("petId") int petId, ModelMap model) {
		return delegate.initUpdateForm(petId, model);
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model) {
		return delegate.processUpdateForm(pet, result, owner, model);
	}

}
