package houzm.accumulation.lua.inaction;

/**
 * author: hzm_dream@163.com
 * date: 2018/12/20 16:23
 * description: lua
 */
public enum LuaContents {
    ;
    private String name;
    private String scriptContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }
}
