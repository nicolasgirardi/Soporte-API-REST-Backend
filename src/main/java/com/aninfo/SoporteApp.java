package com.aninfo;

import com.aninfo.model.TaskTicketAssociation;
import com.aninfo.model.Ticket;
import com.aninfo.service.TaskTicketAssociationService;
import com.aninfo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class SoporteApp {

	/* servicios */
	@Autowired
	private TicketService ticketService;
	@Autowired
	private TaskTicketAssociationService ticketAssociationService;


	public static void main(String[] args) {
		SpringApplication.run(SoporteApp.class, args);
	}

	/* END-POINTS */
	@PostMapping("/Tickets")
	@ResponseStatus(HttpStatus.CREATED)
	public Ticket createTicket(@RequestBody Ticket ticket) {return ticketService.createTicket(ticket);}

	@GetMapping("/Ticket/{productId}/{versionId}")
	public Collection<Ticket> getTickets(@PathVariable long productId, @PathVariable long versionId) {return ticketService.getTickets(productId,versionId);}

	@PutMapping("/Ticket")
	public ResponseEntity<Ticket>  updateTicket(@RequestBody Ticket ticket) {
		long productId = ticket.getproductId();
		long versionId = ticket.getversionId();
		return ticketService.updateTicket(ticket,productId,versionId);
	}
	@PostMapping("/Ticket/Asociation")
	@ResponseStatus(HttpStatus.CREATED)
	public TaskTicketAssociation association(@RequestBody TaskTicketAssociation taskTicket){
		return ticketAssociationService.createTaskTicketAssociation(taskTicket);
	}

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
