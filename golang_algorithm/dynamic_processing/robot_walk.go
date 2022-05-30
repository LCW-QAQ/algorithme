package dynamic_processing

type RobotWalk struct {
}

/*
n 数组长度
start 起始位置
aim 目标位置
k 需要走几步
问有多少种走法可以走到目标位置
*/
func (rw *RobotWalk) walkWays1(n, start, aim, k int) int {
	var process func(cur, rest, aim, n int) int
	process = func(cur, rest, aim, n int) int {
		// 走不了了
		if rest == 0 {
			if cur == aim {
				return 1
			} else {
				return 0
			}
		}
		if cur == 1 {
			return process(2, rest-1, aim, n)
		} else if cur == n {
			return process(n-1, rest-1, aim, n)
		}
		return process(cur-1, rest-1, aim, n) + process(cur+1, rest-1, aim, n)
	}
	return process(start, k, aim, n)
}

/*
事实上ans只跟start与k有关，n有aim是固定值，所以状态信息get。
状态信息是：start与k
*/
func (rw *RobotWalk) walkWaysDP(n, start, aim, k int) int {
	// n + 1行，k + 1列
	dp := make([][]int, n+1)
	for i := range dp {
		dp[i] = make([]int, k+1)
	}
	dp[aim][0] = 1
	// 遍历列
	for rest := 1; rest <= k; rest++ {
		// 遍历行
		dp[1][rest] = dp[2][rest-1]
		for cur := 2; cur < n; cur++ {
			dp[cur][rest] = dp[cur-1][rest-1] + dp[cur+1][rest-1]
		}
		dp[n][rest] = dp[n-1][rest-1]
	}
	return dp[start][k]
}
