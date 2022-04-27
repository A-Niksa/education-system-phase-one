package utils.database.testdata;

import logic.models.abstractions.University;
import utils.database.tools.DatabaseWriter;
import utils.logging.LogIdentifier;
import utils.logging.MasterLogger;

public class TestDataBuilder {
    private static TestDataBuilder builder;

    University sharifUniversity;
    private MathDeptBuilder mathDeptBuilder;
    private PhysicsDeptBuilder physicsDeptBuilder;
    private EconomicsDeptBuilder economicsDeptBuilder;
    private ChemistryDeptBuilder chemistryDeptBuilder;
    private AerospaceDeptBuilder aerospaceDeptBuilder;

    private TestDataBuilder() {
        sharifUniversity = new University();
    }

    private static TestDataBuilder getInstance() {
        if (builder == null) {
            builder = new TestDataBuilder();
        }
        return builder;
    }

    public static void build() {
        getInstance().buildByInstance();
    }

    private void buildByInstance() {
        mathDeptBuilder = new MathDeptBuilder(sharifUniversity);
        physicsDeptBuilder = new PhysicsDeptBuilder(sharifUniversity);
        economicsDeptBuilder = new EconomicsDeptBuilder(sharifUniversity);
        chemistryDeptBuilder = new ChemistryDeptBuilder(sharifUniversity);
        aerospaceDeptBuilder = new AerospaceDeptBuilder(sharifUniversity);

        DatabaseWriter.updateDatabase();
        MasterLogger.log("end of program", LogIdentifier.INFO, "buildByInstance",
                "utils.database.testdata.TestDataBuilder");
    }
}