package org.nutz.el2.opt.arithmetic;

import org.nutz.el2.opt.TwoTernary;

/**
 * 取模
 * @author juqkai(juqkai@gmail.com)
 *
 */
public class ModOpt extends TwoTernary {
	public int fetchPriority() {
		return 3;
	}
	public Object calculate() {
		Number lval = (Number) calculateItem(this.left);
		Number rval = (Number) calculateItem(this.right);
		if(rval instanceof Double || lval instanceof Double){
			return lval.doubleValue() % rval.doubleValue();
		}
		if(rval instanceof Float || lval instanceof Float){
			return lval.floatValue() % rval.floatValue();
		}
		if(rval instanceof Long || lval instanceof Long){
			return lval.longValue() % rval.longValue();
		}
		return lval.intValue() % rval.intValue();
	}

	public String fetchSelf() {
		return "%";
	}

}
