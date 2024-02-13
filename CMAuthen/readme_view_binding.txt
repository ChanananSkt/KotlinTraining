# Setup at build.gradle at app level

    buildFeatures {
       ...
       viewBinding = true
    }

    and SyncNow

# Case of Activity
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
    }

# Case of Fragment
    private lateinit var binding: FragmentChartBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View? {
            binding = FragmentChartBinding.inflate(layoutInflater)