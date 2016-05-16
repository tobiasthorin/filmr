package filmr.services;

import filmr.domain.Row;
import filmr.domain.Seat;
import filmr.domain.SeatState;
import filmr.domain.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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

	public void updateRowsAndSeats(Theater theater, Integer new_number_of_rows, Integer new_max_row_size){

		List<Row> rows = theater.getRows();
		Integer oldnumberOfRows = rows.size();
		Integer oldRowSize = rows.get(0).getSeats().size();



		if(oldnumberOfRows < new_number_of_rows){
			Integer numberOfRowsToAdd = new_number_of_rows-oldnumberOfRows;
			addRows(rows,numberOfRowsToAdd);
		}else{
			Integer numberOfRowsToRemove = oldnumberOfRows - new_number_of_rows;
			removeRows(rows,numberOfRowsToRemove);
		}

		if(oldRowSize<new_max_row_size){
			addSeatsToRow(rows,new_max_row_size);
		}else{
			Integer numberOfSeatsToRemovePerRow = new_max_row_size - oldRowSize;
			removeSeatsInRows(rows, numberOfSeatsToRemovePerRow);
		}


	}

	private void removeSeatsInRows(List<Row> rows, Integer numberOfSeatsToRemovePerRow) {
		for (Row row : rows){
			for(int i = 0; i < numberOfSeatsToRemovePerRow; i++){
				int lastIndex = row.getSeats().size()-1;
				//rowService.deleteEntity(row.getSeats().get(lastIndex).getId());
				entityManager.remove(entityManager.merge(row.getSeats().get(lastIndex)));

			}
		}
	}

	private void addSeatsToRow(List<Row> rows, Integer new_max_row_size) {
		for(Row row : rows){
			Integer numberOfSeatsToAddPerRow = new_max_row_size - row.getSeats().size();
			if(row.getSeats().size()< new_max_row_size){
				for(int i = 0; i<numberOfSeatsToAddPerRow; i++){
					Seat seat = new Seat();
					seat.setState(SeatState.ENABLED);
					row.getSeats().add(seat);
				}
			}
		}
	}

	private void removeRows(List<Row> rows, Integer numberOfRowsToRemove) {

		for(int i = rows.size()-1; i>rows.size()-numberOfRowsToRemove; i--){
			entityManager.remove(entityManager.merge(rows.get(i)));
		}

	}

	private void addRows(List<Row> rows, Integer numberOfRowsToAdd) {
		Integer currentRowSize = rows.get(0).getSeats().size();
		for(int i = 0; i<numberOfRowsToAdd; i++){
			Row row = new Row();
			row.setSeats(new ArrayList<>());
			for(int j = 0; j<currentRowSize; j++){
				Seat seat = new Seat();
				seat.setState(SeatState.ENABLED);
				row.getSeats().add(seat);
			}
			rows.add(row);
		}
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
