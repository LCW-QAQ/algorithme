package dynamic_processing

import "testing"

func Test_whoWin(t *testing.T) {
	p := PrePostCardPoint{}
	res := p.whoWin([]int{10, 20, 100, 50})
	t.Log(res)
}

func Test_whoWinMS(t *testing.T) {
	p := PrePostCardPoint{}
	res := p.whoWinMS([]int{10, 20, 100, 50})
	t.Log(res)
}
