import 'com.alisoft.nb.Nano'

class NanobenchTest < Test::Unit::TestCase
	def test_nano_bench
		task = java.lang.Thread.new {}
		Nano.bench.measure(task)
	end
end