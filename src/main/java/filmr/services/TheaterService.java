package filmr.services;

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

@Service
public class TheaterService extends BaseServiceClass<Theater, Long> {

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
		for(int i=0; i<number_of_rows; i++){
			Row row = new Row();
			row.setSeats(new ArrayList<Seat>() );
		
			row.setTheater(theater);

			for(int j=0; j < max_row_size; j++) {
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
		
		for(Row row : theater.getRows()){
			
			row.setRowLabel("row " + rowCounter);
			rowCounter++;

			// name seats in row
			for(Seat seat : row.getSeats()) {
				SeatState seatState = seat.getState();
				
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
			}
			
			if(resetSeatNumberingsAfterEachRow) {
				seatCounter = 1;
			}
		}
		
		
	}
	
	
}
