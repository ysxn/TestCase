
package in.srain.cube.views.ptr;

import in.srain.cube.views.ptr.PullWidget.OnPullUIListener;

/**
 * A single linked list to wrap PtrUIHandler
 */
class UIHandlerHolder implements OnPullUIListener {

    private OnPullUIListener mHandler;
    private UIHandlerHolder mNext;

    private boolean contains(OnPullUIListener handler) {
        return mHandler != null && mHandler == handler;
    }

    private UIHandlerHolder() {

    }

    public boolean hasHandler() {
        return mHandler != null;
    }

    private OnPullUIListener getHandler() {
        return mHandler;
    }

    public static void addHandler(UIHandlerHolder head, OnPullUIListener handler) {

        if (null == handler) {
            return;
        }
        if (head == null) {
            return;
        }
        if (null == head.mHandler) {
            head.mHandler = handler;
            return;
        }

        UIHandlerHolder current = head;
        for (;; current = current.mNext) {

            // duplicated
            if (current.contains(handler)) {
                return;
            }
            if (current.mNext == null) {
                break;
            }
        }

        UIHandlerHolder newHolder = new UIHandlerHolder();
        newHolder.mHandler = handler;
        current.mNext = newHolder;
    }

    public static UIHandlerHolder create() {
        return new UIHandlerHolder();
    }

    public static UIHandlerHolder removeHandler(UIHandlerHolder head, OnPullUIListener handler) {
        if (head == null || handler == null || null == head.mHandler) {
            return head;
        }

        UIHandlerHolder current = head;
        UIHandlerHolder pre = null;
        do {

            // delete current: link pre to next, unlink next from current;
            // pre will no change, current move to next element;
            if (current.contains(handler)) {

                // current is head
                if (pre == null) {

                    head = current.mNext;
                    current.mNext = null;

                    current = head;
                } else {

                    pre.mNext = current.mNext;
                    current.mNext = null;
                    current = pre.mNext;
                }
            } else {
                pre = current;
                current = current.mNext;
            }

        } while (current != null);

        if (head == null) {
            head = new UIHandlerHolder();
        }
        return head;
    }

    @Override
    public void onUIReset(PullWidget frame) {
        UIHandlerHolder current = this;
        do {
            final OnPullUIListener handler = current.getHandler();
            if (null != handler) {
                handler.onUIReset(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshPrepare(PullWidget frame) {
        if (!hasHandler()) {
            return;
        }
        UIHandlerHolder current = this;
        do {
            final OnPullUIListener handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshPrepare(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshBegin(PullWidget frame) {
        UIHandlerHolder current = this;
        do {
            final OnPullUIListener handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshBegin(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshComplete(PullWidget frame) {
        UIHandlerHolder current = this;
        do {
            final OnPullUIListener handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshComplete(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIPositionChange(PullWidget frame, boolean isUnderTouch, byte status, PullViewManager ptrIndicator) {
        UIHandlerHolder current = this;
        do {
            final OnPullUIListener handler = current.getHandler();
            if (null != handler) {
                handler.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
            }
        } while ((current = current.mNext) != null);
    }
}
