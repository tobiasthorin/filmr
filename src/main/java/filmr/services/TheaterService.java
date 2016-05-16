package filmr.services;

import filmr.controllers.BookingController;
import filmr.domain.Row;
import filmr.domain.Seat;
import filmr.domain.SeatState;
import filmr.domain.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

@Service
public class TheaterService extends BaseServiceClass<Theater, Long> {

	private static org.apache.log4j.Logger log = Logger.getLogger(TheaterService.class);

	@PersistenceContext
	private EntityManager entityManager;

	public List<Theater> readfilteredEntries(Long cinema_id) {

		Query query = entityManager.createNamedQuery("Theater.filterByCinema", Theater.class);
		query.setParameter("cinemaId", cinema_id);

		List<Theater> results = query.getResultList();

		return results;
	}
	public Theater buildTheaterWithRowsAndSeats(Theater theater, Integer number_of_rows, Integer max_row_size){

		theater.setRows(new ArrayList<Row>());
		if(log.isDebugEnabled()) log.debug("foreach number of rows");
		for(int i=0; i<number_of_rows; i++){
			if(log.isDebugEnabled()) log.debug("row "+i);

			if(log.isDebugEnabled()) log.debug("creating row object (set theater and init seat array)");
			Row row = new Row();
			row.setSeats(new ArrayList<Seat>() );
			row.setTheater(theater);

			for(int j=0; j < max_row_size; j++) {
				if(log.isDebugEnabled()) log.debug("creating seat object (add to row and init)");
				Seat seat = new Seat();
				seat.setState(SeatState.ENABLED);
				row.getSeats().add(seat);
				seat.setRow(row);
			}
			theater.getRows().add(row);
		}
		
		nameRowsAndSeats(theater, true);

		return theater;
	}
	
	public void nameRowsAndSeats(Theater theater, boolean resetSeatNumberingsAfterEachRow) {

		int rowCounter = 1;
		int seatCounter = 1;
		log.debug("init counters: rowCounter = "+rowCounter+", seatCounter = "+seatCounter);

		if(log.isDebugEnabled()) log.debug("foreach rows");
		for(Row row : theater.getRows()){

			if(log.isDebugEnabled()) log.debug("fetch row. rowCounter = "+rowCounter);

			String rowName = "row " + rowCounter;
			if(log.isDebugEnabled()) log.debug("set row name "+rowName);
			row.setRowLabel(rowName);

			rowCounter++;

			// name seats in row
			if(log.isDebugEnabled()) log.debug("foreach seats");
			for(Seat seat : row.getSeats()) {
				if(log.isDebugEnabled()) log.debug("fetch seat. rowName = "+rowName+", rowCounter = "+rowCounter+", seatCounter = "+seatCounter);
				SeatState seatState = seat.getState();
				if(log.isDebugEnabled()) log.debug("current seat state :"+seatState);

				switch (seatState) {
				// both enabled and disabled seats should be numbered, but not non-seats
					case ENABLED:
					case DISABLED: {
						seat.setSeatLabel("" + seatCounter);
						seatCounter++;
						break;
				}
					default: {
						seat.setSeatLabel("N/A");
						break;
					}

				
				}
				if(log.isDebugEnabled()) log.debug("after set seat label :"+seat.getSeatLabel());
			}
			
			if(resetSeatNumberingsAfterEachRow) {
				if(log.isDebugEnabled()) log.debug("reset seat counter");
				seatCounter = 1;
			}
		}
		
		
	}
	
	
}
