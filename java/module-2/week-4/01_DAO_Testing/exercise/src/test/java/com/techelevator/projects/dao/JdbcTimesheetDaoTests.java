package com.techelevator.projects.dao;

import com.techelevator.projects.model.Timesheet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class JdbcTimesheetDaoTests extends BaseDaoTests {

    private static final Timesheet TIMESHEET_1 = new Timesheet(1, 1, 1,
            LocalDate.parse("2021-01-01"), 1.0, true, "Timesheet 1");
    private static final Timesheet TIMESHEET_2 = new Timesheet(2, 1, 1,
            LocalDate.parse("2021-01-02"), 1.5, true, "Timesheet 2");
    private static final Timesheet TIMESHEET_3 = new Timesheet(3, 2, 1,
            LocalDate.parse("2021-01-01"), 0.25, true, "Timesheet 3");
    private static final Timesheet TIMESHEET_4 = new Timesheet(4, 2, 2,
            LocalDate.parse("2021-02-01"), 2.0, false, "Timesheet 4");
    
    private JdbcTimesheetDao dao;


    @Before
    public void setup() {
        dao = new JdbcTimesheetDao(dataSource);
    }

    @Test
    public void getTimesheet_returns_correct_timesheet_for_id() {


        Timesheet testTimesheet = dao.getTimesheet(TIMESHEET_1.getTimesheetId());

        Assert.assertNotNull("getTimesheet(" + TIMESHEET_1.getTimesheetId() + ") returned null", testTimesheet);
        assertTimesheetsMatch( TIMESHEET_1, testTimesheet);

        testTimesheet = dao.getTimesheet(TIMESHEET_4.getTimesheetId());
        assertTimesheetsMatch(TIMESHEET_4, testTimesheet);
    }

    @Test
    public void getTimesheet_returns_null_when_id_not_found() {
        Timesheet testTimesheet = dao.getTimesheet(-1);

        Assert.assertNull(testTimesheet);
    }

    @Test
    public void getTimesheetsByEmployeeId_returns_list_of_all_timesheets_for_employee() {
        List<Timesheet> timesheets = dao.getTimesheetsByEmployeeId(TIMESHEET_1.getEmployeeId());

        Assert.assertEquals(2, timesheets.size());
    }

    @Test
    public void getTimesheetsByProjectId_returns_list_of_all_timesheets_for_project() {
        List<Timesheet> timesheets = dao.getTimesheetsByProjectId(TIMESHEET_1.getProjectId());

        Assert.assertEquals(3, timesheets.size());
    }

    @Test
    public void createTimesheet_returns_timesheet_with_id_and_expected_values() {
        Timesheet newTimesheet =new Timesheet();
        newTimesheet.setEmployeeId(2);
        newTimesheet.setProjectId(2);
        newTimesheet.setDateWorked(LocalDate.parse("2021-01-02"));
        newTimesheet.setHoursWorked(7);
        newTimesheet.setBillable(false);

        Timesheet createdTimesheet = dao.createTimesheet(newTimesheet);


        Assert.assertNotEquals(0, createdTimesheet.getTimesheetId());
        Assert.assertEquals(newTimesheet.getEmployeeId(), createdTimesheet.getEmployeeId());
        Assert.assertEquals(newTimesheet.getProjectId(), createdTimesheet.getProjectId());
        Assert.assertEquals(newTimesheet.getDateWorked(), createdTimesheet.getDateWorked());
        Assert.assertEquals(newTimesheet.getHoursWorked(), createdTimesheet.getHoursWorked(), 0.001);
        Assert.assertEquals(newTimesheet.isBillable(), createdTimesheet.isBillable());
        Assert.assertEquals(newTimesheet.getDescription(), createdTimesheet.getDescription());
    }

    @Test
    public void created_timesheet_has_expected_values_when_retrieved() {
        Timesheet newTimesheet =new Timesheet();
        newTimesheet.setEmployeeId(2);
        newTimesheet.setProjectId(2);
        newTimesheet.setDateWorked(LocalDate.parse("2021-01-02"));
        newTimesheet.setHoursWorked(3);
        newTimesheet.setBillable(true);

        Timesheet createdTimesheet = dao.createTimesheet(newTimesheet);
        int newId = createdTimesheet.getTimesheetId();
        Timesheet retrievedTimesheet = dao.getTimesheet(newId);

        assertTimesheetsMatch(createdTimesheet, retrievedTimesheet);


    }

    @Test
    public void updated_timesheet_has_expected_values_when_retrieved() {
        Timesheet timesheetToUpdate = dao.getTimesheet(TIMESHEET_1.getTimesheetId());
        timesheetToUpdate.setDescription("Timesheet 1!!");
        timesheetToUpdate.setEmployeeId(2);
        timesheetToUpdate.setBillable(false);

        dao.updateTimesheet(timesheetToUpdate);
        Timesheet updatedTimesheet = dao.getTimesheet(TIMESHEET_1.getTimesheetId());

        assertTimesheetsMatch(timesheetToUpdate, updatedTimesheet);
    }

    @Test
    public void deleted_timesheet_cant_be_retrieved() {
        dao.deleteTimesheet(TIMESHEET_1.getTimesheetId());

        Timesheet timesheet = dao.getTimesheet(TIMESHEET_1.getTimesheetId());
        Assert.assertNull(timesheet);

        dao.deleteTimesheet(TIMESHEET_3.getTimesheetId());

        timesheet = dao.getTimesheet(TIMESHEET_3.getTimesheetId());
        Assert.assertNull(timesheet);
    }

    @Test
    public void getBillableHours_returns_correct_total() {
        double billableHours = dao.getBillableHours(TIMESHEET_1.getEmployeeId(), TIMESHEET_1.getProjectId());

        Assert.assertEquals(2.5, billableHours, 0.001);

        billableHours = dao.getBillableHours(TIMESHEET_1.getEmployeeId(), TIMESHEET_4.getProjectId());

        Assert.assertEquals(0, billableHours, 0.001);

        billableHours = dao.getBillableHours(2, 2);
        Assert.assertEquals(0, billableHours, 0.001);
    }

    private void assertTimesheetsMatch(Timesheet expected, Timesheet actual) {
        Assert.assertEquals(expected.getTimesheetId(), actual.getTimesheetId());
        Assert.assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        Assert.assertEquals(expected.getProjectId(), actual.getProjectId());
        Assert.assertEquals(expected.getDateWorked(), actual.getDateWorked());
        Assert.assertEquals(expected.getHoursWorked(), actual.getHoursWorked(), 0.001);
        Assert.assertEquals(expected.isBillable(), actual.isBillable());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
    }

}
