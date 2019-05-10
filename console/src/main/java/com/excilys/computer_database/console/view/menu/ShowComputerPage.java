package com.excilys.computer_database.console.view.menu;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.console.view.MenuPage;
import com.excilys.computer_database.console.view.Page;
import com.excilys.computer_database.service.service.ComputerService;

@Component
public class ShowComputerPage extends Page {
	
	@Autowired
	private ComputerService service;
	
	@Autowired
	private ShowComputerPage that;
	
	@Autowired
	private MenuPage menuPage;

	private ShowComputerPage() {}
	
	@Override
	protected Optional<Page> backToMenu() {
		return Optional.of(menuPage);
	}

	@Override
	public String show() {
		System.out.println(boxMessage(String.format("Please give a computer id (%s)", ABORT)));
		
		return this.scan.nextLine();
	}

	private Optional<Page> initialCheck(String input) {
		if (input == null || input.equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(that);
		}
		
		if (input.equals("abort")) {
			System.out.println(boxMessage(String.format("[Aborted] %s", BACK_MENU)));
			return backToMenu();
		}
		
		return Optional.empty();
	}

	private Optional<Page> invalidInput(Optional<Integer> idInput) {
		if (idInput.isEmpty()) {
			System.out.println(boxMessage("Invalid id: must be a number"));
			return Optional.of(that);
		}
		
		if (idInput.get() <= 0) {
			System.out.println(boxMessage("Invalid id: must be > 0"));
			return Optional.of(that);
		}
		
		return Optional.empty();
	}

	@Override
	public Optional<Page> exec(String input) {
		Optional<Page> next = initialCheck(input);
		
		if (next.isPresent())
			return next;
		
		Optional<Integer> idInput = Util.parseInt(input);
		
		Optional<Page> isInvalid = invalidInput(idInput);
		
		if (isInvalid.isPresent())
			return isInvalid;
		
		Optional<ComputerDTO> computer = service.getById(input);
		
		if (computer.isPresent()) {
			System.out.println(boxMessage("Here's the "+idInput.get()+" computer"));
			System.out.println(computer.get());
		} else {
			System.out.println((boxMessage("There is no computer with this id")));
		}
		
		System.out.println(boxMessage(M_BACK_MENU));
		
		return backToMenu();
	}
	
	public String toString() {
		return "Show computer";
	}
}