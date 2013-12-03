package storyrip
import groovy.json.JsonOutput
class Util {
  static void pprintln(x) {
    println JsonOutput.prettyPrint(JsonOutput.toJson(x))
  }
}
