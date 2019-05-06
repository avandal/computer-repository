package com.excilys.computer_database.console.view;

import static com.excilys.computer_database.binding.util.Util.boxMessage;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computer_database.binding.dto.ComputerDTO;
import com.excilys.computer_database.binding.dto.ComputerDTOBuilder;
import com.excilys.computer_database.binding.util.Util;
import com.excilys.computer_database.service.service.ComputerService;
import com.excilys.computer_database.service.service.exception.FailComputerException;

@Component
public class CreateComputerPage extends Page {
	private final static String MSG_NAME = "Please give a computer name ('abort' to abort)";
	private final static String MSG_INTRODUCED = "Please give an introduced date ('abort' to abort, "
												+ "'null' to set null)";
	private final static String MSG_DISCONTINUED = "Please give a discontinued date ('abort' to abort, "
												+ "'null' to set null)";
	private final static String MSG_IDCOMP = "Please give a company id (an integer, 'abort' to abort, "
												+ "'null' to set null)";

	private String nameComp;
	private String introducedComp;
	private String discontinuedComp;
	private String companyIdComp;

	private int index = 1;
	
	@Autowired
	private ComputerService service;
	
	@Autowired
	private MenuPage menuPage;
	
	private CreateComputerPage() {}

	@Override
	public String show() {
		switch (this.index) {
		case 1:
			System.out.println(boxMessage(MSG_NAME));
			break;
		case 2:
			System.out.println(boxMessage(MSG_INTRODUCED));
			break;
		case 3:
			System.out.println(boxMessage(MSG_DISCONTINUED));
			break;
		case 4:
			System.out.println(boxMessage(MSG_IDCOMP));
			break;
		default:
			break;
		}

		String input = this.scan.nextLine();

		return input;
	}

	private boolean execName(String input) {
		this.nameComp = input;
		return true;
	}

	private void setTimestamp(TimestampChoice choice, String time) {
		switch (choice) {
		case INTRODUCED: this.introducedComp = time; break;
		case DISCONTINUED: this.discontinuedComp = time; break;
		default: break;
		}
	}

	private boolean execTimestamp(TimestampChoice timestamp, String input) {
		if (input.equals("null")) {
			setTimestamp(timestamp, null);
			return true;
		}

		Optional<Timestamp> time = Util.dateToTimestamp(input);

		if (time.isEmpty()) {
			System.out.println(boxMessage("Wrong format"));
			return false;
		}

		setTimestamp(timestamp, input);
		return true;
	}

	private boolean execCompany(String input) {
		if (input.equals("null")) {
			this.companyIdComp = null;
			return true;
		}

		Optional<Integer> id = Util.parseInt(input);

		if (id.isEmpty()) {
			System.out.println(boxMessage("Must be an integer"));
			return false;
		}

		this.companyIdComp = input;
		return true;
	}

	@Override
	public Optional<Page> exec(String input) {
		if (input == null || input.trim().equals("")) {
			System.out.println(boxMessage("Invalid input"));
			return Optional.of(this);
		}

		if (input.equals("abort")) {
			System.out.println(boxMessage("[Aborted] " + BACK_MENU));
			return Optional.of(menuPage);
		}

		boolean next = false;
		boolean finished = false;

		switch (this.index) {
		case 1: next = execName(input); break;

		case 2: next = execTimestamp(TimestampChoice.INTRODUCED, input); break;

		case 3: next = execTimestamp(TimestampChoice.DISCONTINUED, input); break;

		case 4: finished = execCompany(input); break;

		default: System.out.println(boxMessage("[Undefined problem]")); break;
		}

		if (finished) {
			ComputerDTO computer = new ComputerDTOBuilder()
					.empty()
					.name(nameComp)
					.introduced(introducedComp)
					.discontinued(discontinuedComp)
					.companyId(companyIdComp)
					.build();
			
			try {
				service.create(computer);
			} catch (FailComputerException e) {
				e.printStackTrace();
			}
			
			index = 1;

			return Optional.of(menuPage);
		}

		if (next) {
			index++;
		}

		return Optional.of(this);
	}
	
	public String toString() {
		return "CreateComputer";
	}
}