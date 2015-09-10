package com.databasepreservation.testing.unit.cli;

import com.databasepreservation.modules.DatabaseExportModule;
import com.databasepreservation.modules.DatabaseImportModule;
import com.databasepreservation.modules.postgreSql.PostgreSQLModuleFactory;
import com.databasepreservation.modules.postgreSql.in.PostgreSQLJDBCImportModule;
import com.databasepreservation.modules.postgreSql.out.PostgreSQLJDBCExportModule;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Bruno Ferreira <bferreira@keep.pt>
 */
@Test(groups = {"cli"}) public class PostgreSQLModuleFactoryTest {
        private static Class<? extends DatabaseImportModule> importModuleClass = PostgreSQLJDBCImportModule.class;
        private static Class<? extends DatabaseExportModule> exportModuleClass = PostgreSQLJDBCExportModule.class;

        private static ModuleFactoryTestHelper testHelper = new ModuleFactoryTestHelper(PostgreSQLModuleFactory.class,
          importModuleClass, exportModuleClass);

        @Test public void arguments_required_long() {
                List<String> args = Arrays
                  .asList("--import=PostgreSQL", "--iusername=name-user", "--ipassword=abc1 23=456",
                    "--ihostname=the-server-name", "--idatabase=dbname", "--export=PostgreSQL",
                    "--eusername=name-another-user", "--epassword=2bcd123=456", "--ehostname=another-server",
                    "--edatabase=another-db-name", "--ido-not-encrypt");

                // test parameters for import module
                HashMap<String, String> expectedValuesImport = new HashMap<String, String>();
                expectedValuesImport.put("hostname", "the-server-name");
                expectedValuesImport.put("database", "dbname");
                expectedValuesImport.put("username", "name-user");
                expectedValuesImport.put("password", "abc1 23=456");
                expectedValuesImport.put("do-not-encrypt", "true");

                // test parameters for export module
                HashMap<String, String> expectedValuesExport = new HashMap<String, String>();
                expectedValuesExport.put("hostname", "another-server");
                expectedValuesExport.put("database", "another-db-name");
                expectedValuesExport.put("username", "name-another-user");
                expectedValuesExport.put("password", "2bcd123=456");

                ModuleFactoryTestHelper
                  .validate_arguments(testHelper, args, expectedValuesImport, expectedValuesExport);
        }

        @Test public void arguments_required_short() {
                List<String> args = Arrays
                  .asList("-i", "PostgreSQL", "-iu", "name-user", "-ip", "abc1 23=456", "-ih", "the-server-name",
                    "-idb", "dbname", "-e", "PostgreSQL", "-eu", "name-another-user", "-ep", "2bcd123=456", "-eh",
                    "another-server", "-edb", "another-db-name", "-ene");

                // test parameters for import module
                HashMap<String, String> expectedValuesImport = new HashMap<String, String>();
                expectedValuesImport.put("hostname", "the-server-name");
                expectedValuesImport.put("database", "dbname");
                expectedValuesImport.put("username", "name-user");
                expectedValuesImport.put("password", "abc1 23=456");
                //expectedValuesImport.put("do-not-encrypt", "");

                // test parameters for export module
                HashMap<String, String> expectedValuesExport = new HashMap<String, String>();
                expectedValuesExport.put("hostname", "another-server");
                expectedValuesExport.put("database", "another-db-name");
                expectedValuesExport.put("username", "name-another-user");
                expectedValuesExport.put("password", "2bcd123=456");
                expectedValuesExport.put("do-not-encrypt", "true");

                ModuleFactoryTestHelper
                  .validate_arguments(testHelper, args, expectedValuesImport, expectedValuesExport);
        }

        @Test public void arguments_portNumber_long() {
                List<String> args = Arrays
                  .asList("--import=PostgreSQL", "--iusername=name-user", "--ipassword=abc1 23=456",
                    "--ihostname=the-server-name", "--idatabase=dbname", "--export=PostgreSQL",
                    "--eusername=name-another-user", "--epassword=2bcd123=456", "--ehostname=another-server",
                    "--edatabase=another-db-name", "--ido-not-encrypt", "--iport-number=1234", "--eport-number=4321");

                // test parameters for import module
                HashMap<String, String> expectedValuesImport = new HashMap<String, String>();
                expectedValuesImport.put("hostname", "the-server-name");
                expectedValuesImport.put("database", "dbname");
                expectedValuesImport.put("username", "name-user");
                expectedValuesImport.put("password", "abc1 23=456");
                expectedValuesImport.put("do-not-encrypt", "true");
                expectedValuesImport.put("port-number", "1234");

                // test parameters for export module
                HashMap<String, String> expectedValuesExport = new HashMap<String, String>();
                expectedValuesExport.put("hostname", "another-server");
                expectedValuesExport.put("database", "another-db-name");
                expectedValuesExport.put("username", "name-another-user");
                expectedValuesExport.put("password", "2bcd123=456");
                expectedValuesExport.put("port-number", "4321");

                ModuleFactoryTestHelper
                  .validate_arguments(testHelper, args, expectedValuesImport, expectedValuesExport);
        }

        @Test public void arguments_portNumber_short() {
                List<String> args = Arrays
                  .asList("-i", "PostgreSQL", "-iu", "name-user", "-ip", "abc1 23=456", "-ih", "the-server-name",
                    "-idb", "dbname", "-e", "PostgreSQL", "-eu", "name-another-user", "-ep", "2bcd123=456", "-eh",
                    "another-server", "-edb", "another-db-name", "-ene", "-ipn", "4567", "-epn", "7654");

                // test parameters for import module
                HashMap<String, String> expectedValuesImport = new HashMap<String, String>();
                expectedValuesImport.put("hostname", "the-server-name");
                expectedValuesImport.put("database", "dbname");
                expectedValuesImport.put("username", "name-user");
                expectedValuesImport.put("password", "abc1 23=456");
                expectedValuesImport.put("port-number", "4567");

                // test parameters for export module
                HashMap<String, String> expectedValuesExport = new HashMap<String, String>();
                expectedValuesExport.put("hostname", "another-server");
                expectedValuesExport.put("database", "another-db-name");
                expectedValuesExport.put("username", "name-another-user");
                expectedValuesExport.put("password", "2bcd123=456");
                expectedValuesExport.put("do-not-encrypt", "true");
                expectedValuesExport.put("port-number", "7654");

                ModuleFactoryTestHelper
                  .validate_arguments(testHelper, args, expectedValuesImport, expectedValuesExport);
        }
}
