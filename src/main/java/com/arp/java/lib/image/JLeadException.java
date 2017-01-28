package com.arp.java.lib.image;

/**
 * JLead クラスのオブジェクト呼び出しで発行される例外。
 *
 * @author Haruo TAKEDA
 */
public class JLeadException extends Exception {
    private int _category;
    private int _status;

    /**
     * デフォルトコンストラクタ
     * @param message エラーメッセージ
     */
    public JLeadException(String message) {
        super(message);
    }

    /**
     * コンストラクタ
     *
     * @param message
     *            エラーメッセージ
     * @param category
     *            エラー種別（Java/JNI）
     * @param status
     *            エラーステータス
     */
    public JLeadException(String message, int category, int status) {
        super(message);
        _category = category;
        _status = status;
    }

    /**
     * エラー種別の文字列を取得する
     * @return エラー種別を示す数値を返します。
     */
    public int getCategory() {
        return _category ;
    }

    /**
     * エラーステータスを取得する
     * @return エラーステータスを示す数値を返します。
     */
    public int getStatus() {
        return _status;
    }

}