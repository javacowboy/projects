package com.javacowboy.owl.survey.data.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OwlData {

	// an enum to keep track of the order to export the data
	public enum COLUMN {
		FILENAME("Orig File"), RECORD("Record"), AREA("Study Area"), FOREST(
				"NF Name"), DISTRICT("RD Name"), PAC_NAME("PAC Name"), PAC_NUMBER(
				"PAC Full No."), RANGE("Mountain Range"), QUADRANT("Quad Name"), MONITOR_TYPE(
				"Monitoring Type"), SUNRISE("Sunrise"), SUNSET("Sunset"), MONTH(
				"Month"), DAY("Day"), YEAR("Year"), OBJECTIVE("Prog. Objc"), SURVEY_TYPE(
				"Survey Type"), SURVEY_NUMBER("Survey No."), SURVEY_OUT(
				"Survey Out."), PREDAWN("Pre-dawn?"), ABORT("Abort?"), COMPL(
				"Compl?"), SURVEY_PERCENT("% Surv."), OBSERVER_ONE("Observer 1"), OBSERVER_TWO(
				"Observer 2"), OBSERVER_THREE("Observer 3"), RESULTS(
				"Visit Results"), START_TIME("Survey Start Time"), END_TIME(
				"Survey End Time"), HOURS("Survey Hours"), HIKE_HOURS(
				"Hiking Hours"), CPRT("CP or RT"), ROUTE_ID("CP or RT ID"), METHOD(
				"Surv. Meth."), CALL_METHOD("Call Meth."), ROUTE_START_TIME(
				"Route Start"), ROUTE_END_TIME("Route End"), ROUTE_TOTAL(
				"Route Total"), MOON("Moon?"), WIND_MIN("Wind min"), WIND_MAX(
				"Wind max"), CC_PERCENT("CC%"), PPT("PPT?"), TEMP_MIN(
				"Temp min"), TEMP_MAX("Temp max"), OBS_TYPE("Obs. Type"), SEX(
				"Sex"), AGE("Age"), SPP("Spp"), RESPONSE_TIME("Resp. Time"), BEARING(
				"Bearing degrees"), DISTANCE("Distance"), UTM_EAST("UTM E"), UTM_NORTH(
				"UTM N"), MICE_COUNT("Mice Used"), NESTING_STATUS(
				"Nesting Status");

		String label;

		private COLUMN(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	public static String getHeader() {
		StringBuilder builder = new StringBuilder();
		for (COLUMN col : COLUMN.values()) {
			builder.append(col.getLabel()).append(",");
		}
		return builder.toString();
	}

	Long id;
	String month;
	String day;
	String year;
	SurveyType surveyType = SurveyType.NIGHT;
	String district;
	File file;
	String forest;
	String observer1;
	String observer2;
	String observer3;
	String range;
	String result;
	String pacName;
	String pacNumber;
	String areaCovered;
	String quadMapName;
	String monitorType;
	String sunriseTime;
	String sunsetTime;
	String surveyNumber;
	String outingNumber;
	String surveyStartTime;
	String surveyEndTime;
	String surveyTotalTime;
	String hikeInStartTime;
	String hikeInEndTime;
	String hikeInTotalTime;
	String hikeOutStartTime;
	String hikeOutEndTime;
	String hikeOutTotalTime;
	String miceUsed;
	String nestingStatus;
	String predawn;
	String aborted;
	String completed;
	List<DetailData> details = new ArrayList<DetailData>();

	public OwlData(File file, Long id) {
		this.file = file;
		this.id = id;
	}

	public String fileNameNoExtension() {
		if (file == null) {
			return "";
		}
		return file.getName().substring(0, file.getName().lastIndexOf("."));
	}

	public String fileName() {
		return fileNameNoExtension();
	}

	public String add(String val1, String val2) {
		Float num1 = null;
		Float num2 = null;
		if (isFloat(val1)) {
			num1 = Float.parseFloat(val1);
		}
		if (isFloat(val2)) {
			num2 = Float.parseFloat(val2);
		}

		if (num1 != null && num2 != null) {
			return "" + (num1 + num2);
		}

		return concat(val1, val2, " + ");
	}

	String concat(String val1, String val2, String delimiter) {
		if (val1 == null && val2 == null) {
			return "";
		}
		if (val1 != null && !val1.equals("") && val2 != null
				&& !val2.equals("")) {
			return val1 + delimiter + val2;
		}
		return val1 != null ? val1 : val2;
	}

	boolean isFloat(String value) {
		if (value == null) {
			return false;
		}
		try {
			Float.parseFloat(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public String toString() {
		if (!details.isEmpty()) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < details.size(); i++) {
				DetailData detail = details.get(i);
				builder.append(toString(detail));
				if (i < details.size() - 1) {
					builder.append("\r\n");
				}
			}
			return builder.toString();
		}
		return toString(null);
	}

	public String lesserValue(String val1, String val2) {
		if (isNumber(val1) && isNumber(val2)) {
			int num1 = Integer.parseInt(val1);
			int num2 = Integer.parseInt(val2);
			return num1 < num2 ? val1 : val2;
		}
		return val1; // assume the first is lower
	}

	public String greaterValue(String val1, String val2) {
		if (isNumber(val1) && isNumber(val2)) {
			int num1 = Integer.parseInt(val1);
			int num2 = Integer.parseInt(val2);
			return num1 > num2 ? val1 : val2;
		}
		return val2; // assume the second is higher
	}

	boolean isNumber(Object value) {
		try {
			Integer.parseInt(String.valueOf(value));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public String toString(DetailData detailData) {
		// if the detailData is null, create a new one (all field values will be
		// null/empty)
		if (detailData == null) {
			detailData = new DetailData();
		}

		StringBuilder builder = new StringBuilder();
		for (COLUMN col : COLUMN.values()) {
			switch (col) {
			case MICE_COUNT:
				builder.append(quote(miceUsed));
				break;
			case NESTING_STATUS:
				builder.append(quote(nestingStatus));
				break;
			case RECORD:
				builder.append(quote("" + id));
				break;
			case START_TIME:
				builder.append(quote(surveyStartTime, true));
				break;
			case END_TIME:
				builder.append(quote(surveyEndTime, true));
				break;
			case SUNRISE:
				builder.append(quote(sunriseTime, true));
				break;
			case SUNSET:
				builder.append(quote(sunsetTime, true));
				break;
			case HOURS:
				builder.append(quote(surveyTotalTime));
				break;
			case HIKE_HOURS:
				builder.append(quote(add(hikeInTotalTime, hikeOutTotalTime)));
				break;
			case SURVEY_NUMBER:
				builder.append(quote(surveyNumber));
				break;
			case SURVEY_OUT:
				builder.append(quote(outingNumber));
				break;
			case SURVEY_TYPE:
				builder.append(quote(surveyType.getText()));
				break;
			case PREDAWN:
				builder.append(quote(predawn));
				break;
			case ABORT:
				builder.append(quote(aborted));
				break;
			case COMPL:
				builder.append(quote(completed));
				break;
			case OBSERVER_ONE:
				builder.append(quote(observer1));
				break;
			case OBSERVER_TWO:
				builder.append(quote(observer2));
				break;
			case OBSERVER_THREE:
				builder.append(quote(observer3));
				break;
			case FILENAME:
				builder.append(quote(fileNameNoExtension()));
				break;
			case QUADRANT:
				builder.append(quote(quadMapName));
				break;
			case MONITOR_TYPE:
				builder.append(quote(monitorType));
				break;
			case SURVEY_PERCENT:
				builder.append(quote(areaCovered));
				break;
			case PAC_NAME:
				builder.append(quote(pacName));
				break;
			case PAC_NUMBER:
				builder.append(quote(pacNumber, true));
				break;
			case MONTH:
				builder.append(quote(month));
				break;
			case DAY:
				builder.append(quote(day));
				break;
			case YEAR:
				builder.append(quote(year));
				break;
			case DISTRICT:
				builder.append(quote(district));
				break;
			case FOREST:
				builder.append(quote(forest));
				break;
			case RANGE:
				builder.append(quote(range));
				break;
			case RESULTS:
				builder.append(quote(result));
				break;

			// From DetailData
			// object--------------------------------------------------------------------------
			case ROUTE_ID:
				builder.append(quote(detailData.routeNumber));
				break;
			case METHOD:
				builder.append(quote(detailData.method));
				break;
			case CALL_METHOD:
				builder.append(quote(detailData.callMethod));
				break;
			case ROUTE_START_TIME:
				builder.append(quote(detailData.start, true));
				break;
			case ROUTE_END_TIME:
				builder.append(quote(detailData.end, true));
				break;
			case ROUTE_TOTAL:
				builder.append(quote(detailData.total));
				break;
			case MOON:
				builder.append(quote(detailData.moon));
				break;
			case WIND_MIN:
				builder.append(quote(detailData.getMinWindString()));
				break;
			case WIND_MAX:
				builder.append(quote(detailData.getMaxWindString()));
				break;
			case TEMP_MIN:
				String min = detailData.temp;
				if (min != null && min.contains("-")) {
					min = min.replace("--", "-");
					if (min.split("-").length > 0) {
						String val1 = min.split("-")[0];
						String val2 = min.split("-")[1];
						min = lesserValue(val1, val2);
					}
				}
				builder.append(quote(min));
				break;
			case TEMP_MAX:
				String max = detailData.temp;
				if (max != null && max.contains("-")) {
					max = max.replace("--", "-");
					if (max.split("-").length > 1) {
						String val1 = max.split("-")[0];
						String val2 = max.split("-")[1];
						max = greaterValue(val1, val2);
					}
				}
				builder.append(quote(max));
				break;
			case CC_PERCENT:
				builder.append(quote(detailData.cloud));
				break;
			case PPT:
				builder.append(quote(detailData.water));
				break;
			case OBS_TYPE:
				builder.append(quote(detailData.obsType));
				break;
			case SEX:
				builder.append(quote(detailData.sex));
				break;
			case AGE:
				builder.append(quote(detailData.age));
				break;
			case SPP:
				builder.append(quote(detailData.species));
				break;
			case RESPONSE_TIME:
				builder.append(quote(detailData.time, true));
				break;
			case BEARING:
				builder.append(quote(detailData.bearing));
				break;
			case DISTANCE:
				builder.append(quote(detailData.distance));
				break;
			case UTM_EAST:
				builder.append(quote(detailData.utmE));
				break;
			case UTM_NORTH:
				builder.append(quote(detailData.utmN));
				break;
			// End DetailData
			// object---------------------------------------------------------------------------

			// default meaning the code isn't parsing this value yet
			default:
				builder.append(quote(null));
				break;
			}
			builder.append(comma());
		}
		return builder.toString();
	}

	String comma() {
		return ",";
	}

	String quote(String text) {
		return quote(text, false);
	}

	String quote(String text, boolean preventFormat) {
		if (text == null) {
			text = "";
		}
		text = text.replace("\"", "");
		if (preventFormat) {
			text = preventExcelFormatting(text);
		}
		return "\"" + text + "\"";
	}

	String preventExcelFormatting(String text) {
		if (text == null || text.trim().isEmpty()) {
			return "";
		}
		return "'" + text;
	}

	// setters to prevent data from being overwritten
	public void setQuadMapName(String value) {
		if (this.quadMapName == null || this.quadMapName.isEmpty()) {
			this.quadMapName = value;
		}
	}

	public void setResult(String value) {
		if (this.result == null || this.result.isEmpty()) {
			this.result = value;
		}
	}

	public void setSunriseTime(String value) {
		if (this.sunriseTime == null || this.sunriseTime.isEmpty()) {
			this.sunriseTime = value;
		}
	}

	public void setSunsetTime(String value) {
		if (this.sunsetTime == null || this.sunsetTime.isEmpty()) {
			this.sunsetTime = value;
		}
	}
}
