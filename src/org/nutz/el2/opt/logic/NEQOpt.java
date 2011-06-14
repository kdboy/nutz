package org.nutz.el2.opt.logic;

import org.nutz.el2.opt.TwoTernary;

/**
 * 不等于
 * @author juqkai(juqkai@gmail.com)
 *
 */
public class NEQOpt extends TwoTernary{
	public int fetchPriority() {
		return 6;
	}
	public Object calculate() {
		Object lval = calculateItem(this.left);
		Object rval = calculateItem(this.right);
		return lval != rval;
	}
	public String fetchSelf() {
		return "!=";
	}

}
