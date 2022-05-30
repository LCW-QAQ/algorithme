package dynamic_processing

type PrePostCardPoint struct{}

func (p *PrePostCardPoint) whoWin(arr []int) int {
	var (
		pre  func(arr []int, l, r int) int
		post func(arr []int, l, r int) int
	)
	pre = func(arr []int, l, r int) int {
		if l == r {
			return arr[l]
		}
		lres := arr[l] + post(arr, l+1, r)
		rres := arr[r] + post(arr, l, r-1)
		if lres > rres {
			return lres
		}
		return rres
	}
	post = func(arr []int, l, r int) int {
		// 被先手拿了啊
		if l == r {
			return 0
		}
		lres := pre(arr, l+1, r)
		rres := pre(arr, l, r-1)
		// 注意拿的是更小的那个, 为什么? 先手一定会留下竟可能小的点数
		if lres < rres {
			return lres
		}
		return rres
	}
	preRes, postRes := pre(arr, 0, len(arr)-1), post(arr, 0, len(arr)-1)
	if preRes > postRes {
		return preRes
	}
	return postRes
}

func (p *PrePostCardPoint) whoWinMS(arr []int) int {
	length := len(arr)
	var (
		pre     func(arr []int, l, r int) int
		post    func(arr []int, l, r int) int
		preMap  = make([][]int, length)
		postMap = make([][]int, length)
	)
	for i := 0; i < length; i++ {
		preMap[i] = make([]int, length)
		postMap[i] = make([]int, length)
	}
	for i := range preMap {
		for j := range preMap[i] {
			preMap[i][j] = -1
			postMap[i][j] = -1
		}
	}
	pre = func(arr []int, l, r int) int {
		if preMap[l][r] != -1 {
			return preMap[l][r]
		}
		var ans int
		if l == r {
			ans = arr[l]
		} else {
			lres := arr[l] + post(arr, l+1, r)
			rres := arr[r] + post(arr, l, r-1)
			if lres > rres {
				ans = lres
			} else {
				ans = rres
			}
		}
		preMap[l][r] = ans
		return ans
	}
	post = func(arr []int, l, r int) int {
		if postMap[l][r] != -1 {
			return postMap[l][r]
		}
		var ans int
		// 被先手拿了啊
		if l != r {
			lres := pre(arr, l+1, r)
			rres := pre(arr, l, r-1)
			// 注意拿的是更小的那个, 为什么? 先手一定会留下竟可能小的点数
			if lres < rres {
				ans = lres
			} else {
				ans = rres
			}
		}
		postMap[l][r] = ans
		return ans
	}
	preRes, postRes := pre(arr, 0, len(arr)-1), post(arr, 0, len(arr)-1)
	if preRes > postRes {
		return preRes
	}
	return postRes
}
