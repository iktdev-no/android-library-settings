package no.iktdev.setting.exception

class NoUiComponentsPassed(override val message: String) : Exception()
class IncompatibleComponentPassed(override val message: String) : Exception()