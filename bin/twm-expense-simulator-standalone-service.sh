APP_NAME="twm-expense-simulator-standalone-service"
APP_VERSION="0.0.1-SNAPSHOT"
JAVA_PARAM="-Xmx101m"

BIN_PATH=$PROM_HOME_PARENT/TWM/$APP_NAME/bin     #PROM-HOME-PARENT :: exported in .bashrc
# JAR_PATH=$BIN_PATH/../target/$APP_NAME-$APP_VERSION.jar
JAR_PATH=$BIN_PATH/../target/quarkus-app/quarkus-run.jar

APP_PARAMS="-Dexpense-simulator.banks-file-location=${BIN_PATH}/../landing/banks.active.json -Dexpense-simulator.transactions-file-location=${BIN_PATH}/../landing/transactions.active.json -Dexpense-simulator.simulator-csv-file-location=${BIN_PATH}/../landing/"

echo "Starting '$APP_NAME' with java param: '$JAVA_PARAM', app params: '$APP_PARAMS' at '$JAR_PATH'"
java $JAVA_PARAM $APP_PARAMS -jar $JAR_PATH
