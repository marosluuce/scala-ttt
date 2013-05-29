package scalattt

object Main {
  def main(args: Array[String]) {
    val cli = Cli(Game(Board()), CliIo())
    cli.run
  }
}
